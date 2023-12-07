package org.example.dao;

import org.example.model.Trainee;
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

class TraineeDAOTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @InjectMocks
    private TraineeDAO traineeDAO;

    private Trainee trainee;

    @BeforeEach
    void setUp() throws Exception {
        try (AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this)) {
            when(sessionFactory.getCurrentSession()).thenReturn(session);
            trainee = Trainee.builder()
                    .address("11000 Belgrade")
                    .dateOfBirth(new java.util.Date())
                    .user(User.builder()
                            .isActive(true)
                            .lastName("Biaggi")
                            .firstName("Max")
                            .username("Max.Biaggi")
                            .password("0123456789")
                            .build())
                    .build();
        }
    }

    @Test
    void saveTrainee() {
        // Arrange
        doNothing().when(session).persist(eq(trainee));

        // Act
        Trainee savedTrainee = traineeDAO.save(trainee);

        // Assert
        assertEquals(trainee.getUsername(), savedTrainee.getUsername());
        assertEquals(trainee.getUser().getFirstName(), savedTrainee.getUser().getFirstName());
        verify(session, times(1)).persist(trainee);
    }

    @Test
    void findTraineeByUsername() {
        // Arrange
        Query<Trainee> query;
        query = mock(Query.class);
        when(session.createQuery(anyString(), eq(Trainee.class))).thenReturn(query);
        when(query.setParameter(eq("username"), anyString())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(trainee);

        // Act
        Trainee foundTrainee = traineeDAO.findByUsername("Max.Biaggi");

        // Assert
        assertEquals(trainee, foundTrainee);
        verify(session, times(1)).createQuery(anyString(), eq(Trainee.class));
        verify(query, times(1)).setParameter(eq("username"), anyString());
        verify(query, times(1)).getSingleResult();
    }

    @Test
    void updateTrainee() {
        // Arrange
        when(session.merge(trainee)).thenReturn(trainee);

        // Act
        Trainee updatedTrainee = traineeDAO.update(trainee);

        // Assert
        assertEquals(trainee, updatedTrainee);
        verify(session, times(1)).merge(trainee);
    }

    @Test
    void deleteTrainee() {
        // Arrange
        Query<Long> query;
        query = mock(Query.class);
        when(session.createQuery(anyString(), eq(Long.class))).thenReturn(query);
        when(query.setParameter(eq("username"), anyString())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(1L);

        // Act
        boolean result = traineeDAO.delete("Max.Biaggi");

        // Assert
        assertTrue(result);
        verify(session, times(1)).createQuery(anyString(), eq(Long.class));
        verify(query, times(1)).setParameter(eq("username"), anyString());
        verify(query, times(1)).uniqueResult();
        verify(session, times(1)).remove(any());
    }

    @Test
    void deleteTrainee_NotFound() {
        // Arrange
        Query<Long> query;
        query = mock(Query.class);
        when(session.createQuery(anyString(), eq(Long.class))).thenReturn(query);
        when(query.setParameter(eq("username"), anyString())).thenReturn(query);
        when(query.uniqueResult()).thenReturn(null);

        // Act
        boolean result = traineeDAO.delete("NonExistentUser");

        // Assert
        assertFalse(result);
        verify(session, times(1)).createQuery(anyString(), eq(Long.class));
        verify(query, times(1)).setParameter(eq("username"), anyString());
        verify(query, times(1)).uniqueResult();
        verify(session, never()).remove(any());
    }

    @Test
    void getAllTrainees() {
        // Arrange
        Query<Trainee> query;
        query = mock(Query.class);
        when(session.createQuery(anyString(), eq(Trainee.class))).thenReturn(query);
        when(query.list()).thenReturn(Collections.singletonList(trainee));

        // Act
        List<Trainee> trainees = traineeDAO.getAllTrainees();

        // Assert
        assertEquals(1, trainees.size());
        assertEquals(trainee, trainees.get(0));
        verify(session, times(1)).createQuery(anyString(), eq(Trainee.class));
        verify(query, times(1)).list();
    }
}
