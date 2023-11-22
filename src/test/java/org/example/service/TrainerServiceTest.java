package org.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.example.dao.TrainerDAO;
import org.example.model.Trainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrainerServiceTest {

    private TrainerService trainerService;
    private TrainerDAO trainerDAOMock;

    @BeforeEach
    void setUp() {
        trainerDAOMock = mock(TrainerDAO.class);
        trainerService = new TrainerService(trainerDAOMock);
    }

    @Test
    void createTrainer() {
        Trainer trainer = new Trainer();
        trainerService.createTrainer(trainer);

        // Verify that the save method of TrainerDAO was called
        verify(trainerDAOMock, times(1)).save(trainer);
    }

    @Test
    void getTrainerById() {
        long trainerId = 1L;
        Trainer expectedTrainer = new Trainer();
        when(trainerDAOMock.findById(trainerId)).thenReturn(expectedTrainer);

        Trainer actualTrainer = trainerService.getTrainerById(trainerId);

        // Verify that the findById method of TrainerDAO was called
        verify(trainerDAOMock, times(1)).findById(trainerId);
        // Verify that the returned Trainer is the expected Trainer
        assertEquals(expectedTrainer, actualTrainer);
    }

    @Test
    void updateTrainer() {
        Trainer trainer = new Trainer();
        trainerService.updateTrainer(trainer);

        // Verify that the update method of TrainerDAO was called
        verify(trainerDAOMock, times(1)).update(trainer);
    }

    @Test
    void deleteTrainer() {
        long trainerId = 1L;
        trainerService.deleteTrainer(trainerId);

        // Verify that the delete method of TrainerDAO was called
        verify(trainerDAOMock, times(1)).delete(trainerId);
    }

    @Test
    void getAllTrainers() {
        List<Trainer> expectedTrainers = Collections.singletonList(new Trainer());
        when(trainerDAOMock.getAllTrainers()).thenReturn(expectedTrainers);

        List<Trainer> actualTrainers = trainerService.getAllTrainers();

        // Verify that the getAllTrainers method of TrainerDAO was called
        verify(trainerDAOMock, times(1)).getAllTrainers();
        // Verify that the returned list is the expected list
        assertEquals(expectedTrainers, actualTrainers);
    }
}
