package org.example.dao;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.example.model.Training;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaPath;
import org.hibernate.query.criteria.JpaRoot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;

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

    @Test
    void getTraineeTrainingList() {
        // Arrange
        HibernateCriteriaBuilder criteriaBuilder = mock(HibernateCriteriaBuilder.class);
        JpaCriteriaQuery<Training> criteriaQuery = mock(JpaCriteriaQuery.class);
        JpaRoot<Training> root = mock(JpaRoot.class);
        JpaPath<Object> traineePath = mock(JpaPath.class);
        JpaPath<Object> userPath = mock(JpaPath.class);
        JpaPath<Object> usernamePath = mock(JpaPath.class);
        Query<Training> query = mock(Query.class);

        List<Predicate> predicates = new ArrayList<>();
        List<Training> expectedResult = Collections.singletonList(training);

        when(session.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Training.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Training.class)).thenReturn(root);
        when(root.get("trainee")).thenReturn(traineePath);
        when(traineePath.get("user")).thenReturn(userPath);
        when(userPath.get("username")).thenReturn(usernamePath);
        when(criteriaQuery.select(any())).thenReturn(criteriaQuery);
        when(criteriaQuery.where(predicates.toArray(new Predicate[]{}))).thenReturn(criteriaQuery);
        when(session.createQuery(criteriaQuery)).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedResult);

        // Act
        List<Training> actualResult = trainingDAO.getTraineeTrainingList("Max.Biaggi", 5);

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getTrainerTrainingListTest() {
        // Arrange
        HibernateCriteriaBuilder criteriaBuilder = mock(HibernateCriteriaBuilder.class);
        JpaCriteriaQuery<Training> criteriaQuery = mock(JpaCriteriaQuery.class);
        JpaRoot<Training> root = mock(JpaRoot.class);
        JpaPath<Object> trainerPath = mock(JpaPath.class);
        JpaPath<Object> userPath = mock(JpaPath.class);
        JpaPath<Object> usernamePath = mock(JpaPath.class);
        Query<Training> query = mock(Query.class);

        List<Predicate> predicates = new ArrayList<>();
        List<Training> expectedResult = Collections.singletonList(training);

        when(session.getCriteriaBuilder()).thenReturn(criteriaBuilder);
        when(criteriaBuilder.createQuery(Training.class)).thenReturn(criteriaQuery);
        when(criteriaQuery.from(Training.class)).thenReturn(root);
        when(root.get("trainer")).thenReturn(trainerPath);
        when(trainerPath.get("user")).thenReturn(userPath);
        when(userPath.get("username")).thenReturn(usernamePath);
        when(criteriaQuery.select(any())).thenReturn(criteriaQuery);
        when(criteriaQuery.where(predicates.toArray(new Predicate[]{}))).thenReturn(criteriaQuery);
        when(session.createQuery(criteriaQuery)).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedResult);

        // Act
        List<Training> actualResult = trainingDAO.getTrainerTrainingList("Max.Biaggi", 5);

        // Assert
        assertEquals(expectedResult, actualResult);
    }

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
