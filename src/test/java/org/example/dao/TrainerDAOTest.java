package org.example.dao;

import org.example.model.Trainer;
import org.example.model.TrainingType;
import org.example.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TrainerDAOTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @InjectMocks
    private TrainerDAO trainerDAO;

    private Trainer trainer;

    @BeforeEach
    void setUp() throws Exception {
        try (AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this)) {
            when(sessionFactory.getCurrentSession()).thenReturn(session);
            trainer = Trainer.builder()
                    .specialization(TrainingType.builder()
                            .id(1L)
                            .trainingTypeName("Cardio")
                            .build())
                    .user(User.builder()
                            .isActive(true)
                            .lastName("Smith")
                            .firstName("John")
                            .username("John.Smith")
                            .password("password123")
                            .build())
                    .build();
        }
    }

    @Test
    void saveTrainer() {
        // Arrange
        doNothing().when(session).persist(trainer);

        // Act
        Trainer savedTrainer = trainerDAO.save(trainer);

        // Assert
        assertEquals(trainer.getUsername(), savedTrainer.getUsername());
        assertEquals(trainer.getUser().getFirstName(), savedTrainer.getUser().getFirstName());
        verify(session, times(1)).persist(trainer);
    }

    @Test
    void findTrainerByUsername() {
        // Arrange
        Query<Trainer> query;
        query = mock(Query.class);
        when(session.createQuery(anyString(), eq(Trainer.class))).thenReturn(query);
        when(query.setParameter(eq("username"), anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(trainer);

        // Act
        Trainer foundTrainer = trainerDAO.findByUsername("John.Smith");

        // Assert
        assertEquals(trainer, foundTrainer);
        verify(session, times(1)).createQuery(anyString(), eq(Trainer.class));
        verify(query, times(1)).setParameter(eq("username"), anyString());
        verify(query, times(1)).getSingleResult();
    }

    @Test
    void updateTrainer() {
        // Arrange
        when(session.merge(trainer)).thenReturn(trainer);

        // Act
        Trainer updatedTrainer = trainerDAO.update(trainer);

        // Assert
        assertEquals(trainer, updatedTrainer);
        verify(session, times(1)).merge(trainer);
    }

    @Test
    void deleteTrainer() {
        // Arrange
        Query<Long> subQuery;
        subQuery = mock(Query.class);
        when(session.createQuery(anyString(), eq(Long.class))).thenReturn(subQuery);
        when(subQuery.setParameter(eq("username"), anyString())).thenReturn(subQuery);
        when(subQuery.uniqueResult()).thenReturn(1L);

        // Act
        boolean result = trainerDAO.delete("John.Smith");

        // Assert
        assertTrue(result);
        verify(session, times(1)).createQuery(anyString(), eq(Long.class));
        verify(subQuery, times(1)).setParameter(eq("username"), anyString());
        verify(subQuery, times(1)).uniqueResult();
        verify(session, times(1)).remove(any());
    }

    @Test
    void deleteTrainer_NotFound() {
        // Arrange
        Query<Long> subQuery;
        subQuery = mock(Query.class);
        when(session.createQuery(anyString(), eq(Long.class))).thenReturn(subQuery);
        when(subQuery.setParameter(eq("username"), anyString())).thenReturn(subQuery);
        when(subQuery.uniqueResult()).thenReturn(null);

        // Act
        boolean result = trainerDAO.delete("NonExistentUser");

        // Assert
        assertFalse(result);
        verify(session, times(1)).createQuery(anyString(), eq(Long.class));
        verify(subQuery, times(1)).setParameter(eq("username"), anyString());
        verify(subQuery, times(1)).uniqueResult();
        verify(session, never()).remove(any());
    }

    @Test
    void getNotAssignedTrainers() {
        // Arrange
        Query<Trainer> query;
        query = mock(Query.class);
        when(session.createQuery(anyString(), eq(Trainer.class))).thenReturn(query);
        when(query.setParameter(eq("traineeUsername"), anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(trainer));

        // Act
        List<Trainer> trainers = trainerDAO.getNotAssigned("Trainee1");

        // Assert
        assertEquals(1, trainers.size());
        assertEquals(trainer, trainers.get(0));
        verify(session, times(1)).createQuery(anyString(), eq(Trainer.class));
        verify(query, times(1)).setParameter(eq("traineeUsername"), anyString());
        verify(query, times(1)).getResultList();
    }

    @Test
    void getAllTrainers() {
        // Arrange
        Query<Trainer> query;
        query = mock(Query.class);
        when(session.createQuery(anyString(), eq(Trainer.class))).thenReturn(query);
        when(query.list()).thenReturn(Collections.singletonList(trainer));

        // Act
        List<Trainer> trainers = trainerDAO.getAllTrainers();

        // Assert
        assertEquals(1, trainers.size());
        assertEquals(trainer, trainers.get(0));
        verify(session, times(1)).createQuery(anyString(), eq(Trainer.class));
        verify(query, times(1)).list();
    }
}
