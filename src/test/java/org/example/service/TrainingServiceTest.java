package org.example.service;

import org.example.dao.TrainingDAO;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.model.User;
import org.example.utils.UserAuthentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TrainingServiceTest {

    @Mock
    private TrainingDAO trainingDAO;

    @Mock
    private UserAuthentication userAuthentication;

    @InjectMocks
    private TrainingService trainingService;

    private Training training;

    private Trainee trainee;

    private Trainer trainer;

    private User user1;

    private User user2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trainingService = new TrainingService(trainingDAO);
        ReflectionTestUtils.setField(trainingService, "authentication", userAuthentication);

        user1 = User.builder()
                .isActive(true)
                .lastName("Biaggi")
                .firstName("Max")
                .username("Max.Biaggi")
                .password("0123456789")
                .build();

        user2 = User.builder()
                .isActive(true)
                .lastName("Storrari")
                .firstName("Matteo")
                .username("Matteo.Storrari")
                .password("0123456789")
                .build();

        trainee = Trainee.builder()
                .user(user1)
                .address("11000 Belgrade")
                .dateOfBirth(new Date())
                .trainerList(new ArrayList<>())
                .trainingList(new ArrayList<>())
                .build();

        trainer = Trainer.builder()
                .user(user2)
                .traineeList(new ArrayList<>())
                .trainingList(new ArrayList<>())
                .build();

        training = new Training();
        training.setId(1L);
        training.setTrainee(trainee);
        training.setTrainer(trainer);

        doNothing().when(userAuthentication).authenticateUser(eq(trainee.getUsername()), eq(trainee.getPassword()));
    }

    @Test
    void createTraining() {
        // Arrange
        doNothing().when(trainingDAO).save(any(Training.class));

        // Act
        trainingService.createTraining(training);

        // Assert
        verify(trainingDAO, times(1)).save(training);
        assertEquals(trainee, training.getTrainee());
        assertEquals(trainer, training.getTrainer());
    }

    @Test
    void getTrainingById() {
        // Arrange
        when(trainingDAO.findById(1L)).thenReturn(training);

        // Act
        Training result = trainingService.getTrainingById(1L);

        // Assert
        verify(trainingDAO, times(1)).findById(1L);
        assertEquals(training, result);
    }

    @Test
    void updateTraining() {
        // Arrange
        when(trainingDAO.update(training)).thenReturn(training);

        // Act
        Training result = trainingService.updateTraining(training);

        // Assert
        verify(trainingDAO, times(1)).update(training);
        assertEquals(training, result);
    }

    @Test
    void deleteTraining() {
        // Arrange
        when(trainingDAO.delete(training)).thenReturn(true);

        // Act
        boolean result = trainingService.deleteTraining(training);

        // Assert
        verify(trainingDAO, times(1)).delete(training);
        assertTrue(result);
    }

    @Test
    void getTraineeTrainingList() {
        // Arrange
        int trainingDuration = 10;
        List<Training> expectedTrainingList = Collections.singletonList(training);
        when(trainingDAO.getTraineeTrainingList(trainee.getUsername(), trainingDuration)).thenReturn(expectedTrainingList);

        // Act
        List<Training> result = trainingService.getTraineeTrainingList(trainee.getUsername(), trainee.getPassword(), trainingDuration);

        // Assert
        verify(userAuthentication, times(1)).authenticateUser(eq(trainee.getUsername()), eq(trainee.getPassword()));
        verify(trainingDAO, times(1)).getTraineeTrainingList(trainee.getUsername(), trainingDuration);
        assertEquals(expectedTrainingList, result);
    }

    @Test
    void getTrainerTrainingList() {
        // Arrange
        int trainingDuration = 10;
        List<Training> expectedTrainingList = Collections.singletonList(training);
        when(trainingDAO.getTrainerTrainingList(trainer.getUsername(), trainingDuration)).thenReturn(expectedTrainingList);

        // Act
        List<Training> result = trainingService.getTrainerTrainingList(trainer.getUsername(), trainer.getPassword(), trainingDuration);

        // Assert
        verify(userAuthentication, times(1)).authenticateUser(eq(trainer.getUsername()), eq(trainer.getPassword()));
        verify(trainingDAO, times(1)).getTrainerTrainingList(trainer.getUsername(), trainingDuration);
        assertEquals(expectedTrainingList, result);
    }

    @Test
    void getAllTrainings() {
        // Arrange
        List<Training> expectedTrainingList = Collections.singletonList(training);
        when(trainingDAO.getAllTrainings()).thenReturn(expectedTrainingList);

        // Act
        List<Training> result = trainingService.getAllTrainings();

        // Assert
        verify(trainingDAO, times(1)).getAllTrainings();
        assertEquals(expectedTrainingList, result);
    }

}