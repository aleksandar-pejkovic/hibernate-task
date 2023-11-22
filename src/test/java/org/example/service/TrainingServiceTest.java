package org.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.example.dao.TrainingDAO;
import org.example.model.Training;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrainingServiceTest {

    private TrainingService trainingService;
    private TrainingDAO trainingDAOMock;

    @BeforeEach
    void setUp() {
        trainingDAOMock = mock(TrainingDAO.class);
        trainingService = new TrainingService(trainingDAOMock);
    }

    @Test
    void createTraining() {
        Training training = new Training();
        trainingService.createTraining(training);

        // Verify that the save method of TrainingDAO was called
        verify(trainingDAOMock, times(1)).save(training);
    }

    @Test
    void getTrainingById() {
        long trainingId = 1L;
        Training expectedTraining = new Training();
        when(trainingDAOMock.findById(trainingId)).thenReturn(expectedTraining);

        Training actualTraining = trainingService.getTrainingById(trainingId);

        // Verify that the findById method of TrainingDAO was called
        verify(trainingDAOMock, times(1)).findById(trainingId);
        // Verify that the returned Training is the expected Training
        assertEquals(expectedTraining, actualTraining);
    }

    @Test
    void updateTraining() {
        Training training = new Training();
        trainingService.updateTraining(training);

        // Verify that the update method of TrainingDAO was called
        verify(trainingDAOMock, times(1)).update(training);
    }

    @Test
    void deleteTraining() {
        long trainingId = 1L;
        trainingService.deleteTraining(trainingId);

        // Verify that the delete method of TrainingDAO was called
        verify(trainingDAOMock, times(1)).delete(trainingId);
    }

    @Test
    void getAllTrainings() {
        List<Training> expectedTrainings = Collections.singletonList(new Training());
        when(trainingDAOMock.getAllTrainings()).thenReturn(expectedTrainings);

        List<Training> actualTrainings = trainingService.getAllTrainings();

        // Verify that the getAllTrainings method of TrainingDAO was called
        verify(trainingDAOMock, times(1)).getAllTrainings();
        // Verify that the returned list is the expected list
        assertEquals(expectedTrainings, actualTrainings);
    }
}
