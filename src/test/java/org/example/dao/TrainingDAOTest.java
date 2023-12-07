package org.example.dao;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.model.TrainingType;
import org.example.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TrainingDAOTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @InjectMocks
    private TrainingDAO trainingDAO;

    private Training training;

    @BeforeEach
    void setUp() throws Exception {
        try (AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this)) {
            when(sessionFactory.getCurrentSession()).thenReturn(session);
            training = Training.builder()
                    .trainingDuration(10)
                    .build();
        }
    }

    @Test
    void saveTraining() {
        // Arrange
        doNothing().when(session).persist(training);

        // Act
        Training savedTraining = trainingDAO.save(training);

        // Assert
        assertEquals(training.getId(), savedTraining.getId());
        verify(session, times(1)).persist(training);
    }

    @Test
    void findTrainingById() {
        // Arrange
        when(session.get(Training.class, 1L)).thenReturn(training);

        // Act
        Training foundTraining = trainingDAO.findById(1L);

        // Assert
        assertEquals(training, foundTraining);
    }

/*
    @Test
    void getTraineeTrainingList() {
        // Arrange
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<Training> criteriaQuery = mock(CriteriaQuery.class);
        Query<Training> query = mock(Query.class);
        Root<Training> root = mock(Root.class);
        List<Predicate> predicates = mock(List.class);
        Predicate predicate = mock(Predicate.class);
        Path<?> path = mock(Path.class);

        User user = User.builder()
                .isActive(true)
                .lastName("Biaggi")
                .firstName("Max")
                .username("Max.Biaggi")
                .password("0123456789")
                .build();

        Trainee trainee = Trainee.builder()
                .address("11000 Belgrade")
                .dateOfBirth(new java.util.Date())
                .user(user)
                .build();

        when(sessionFactory.getCriteriaBuilder()).thenReturn((HibernateCriteriaBuilder) criteriaBuilder);
        when(criteriaBuilder.createQuery(Training.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Training.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(criteriaQuery.where(any(Predicate.class))).thenReturn(criteriaQuery);
        when(session.createQuery(criteriaQuery)).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(training));
        when(predicates.add(any())).thenReturn(true);

        // Act
        List<Training> trainings = trainingDAO.getTraineeTrainingList("username", 5);

        // Assert
        assertEquals(1, trainings.size());
        assertEquals(training, trainings.get(0));
    }

    @Test
    void getTraineRTrainingListTest() {
        // Arrange
        CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
        CriteriaQuery<Training> criteriaQuery = mock(CriteriaQuery.class);
        Query<Training> query = mock(Query.class);
        Root<Training> root = mock(Root.class);
        List<Predicate> predicates = mock(List.class);
        Predicate predicate = mock(Predicate.class);
        Path<?> path = mock(Path.class);

        User user = User.builder()
                .isActive(true)
                .lastName("Biaggi")
                .firstName("Max")
                .username("Max.Biaggi")
                .password("0123456789")
                .build();

        Trainer trainer = Trainer.builder()
                .specialization(TrainingType.builder()
                        .trainingTypeName("Cardio")
                        .build())
                .user(user)
                .build();

        when(sessionFactory.getCriteriaBuilder()).thenReturn((HibernateCriteriaBuilder) criteriaBuilder);
        when(criteriaBuilder.createQuery(Training.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Training.class)).thenReturn(root);
        when(criteriaQuery.select(root)).thenReturn(criteriaQuery);
        when(criteriaQuery.where(any(Predicate.class))).thenReturn(criteriaQuery);
        when(session.createQuery(criteriaQuery)).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(training));
        when(predicates.add(any())).thenReturn(true);

        // Act
        List<Training> trainings = trainingDAO.getTrainerTrainingList("username", 5);

        // Assert
        assertEquals(1, trainings.size());
        assertEquals(training, trainings.get(0));
    }
*/

    @Test
    void updateTraining() {
        // Arrange
        when(session.merge(training)).thenReturn(training);

        // Act
        Training updatedTraining = trainingDAO.update(training);

        // Assert
        assertEquals(training, updatedTraining);
        verify(session, times(1)).merge(training);
    }

    @Test
    void deleteTraining() {
        // Arrange
        when(session.merge(training)).thenReturn(training);
        doNothing().when(session).remove(any());

        // Act
        boolean result = trainingDAO.delete(training);

        // Assert
        assertTrue(result);
        verify(session, times(1)).merge(training);
        verify(session, times(1)).remove(training);
    }

    @Test
    void deleteTraining_NotFound() {
        // Arrange
        when(session.merge(any())).thenReturn(training);
        doThrow(new EntityNotFoundException("Entity not found")).when(session).remove(any());

        // Act
        boolean result = trainingDAO.delete(training);

        // Assert
        assertFalse(result);
        verify(session, times(1)).merge(training);
        verify(session, times(1)).remove(training);
    }

    @Test
    void getAllTrainings() {
        // Arrange
        Query<Training> query;
        query = mock(Query.class);
        when(session.createQuery(anyString(), eq(Training.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(training));

        // Act
        List<Training> trainings = trainingDAO.getAllTrainings();

        // Assert
        assertEquals(1, trainings.size());
        assertEquals(training, trainings.get(0));
    }

}
