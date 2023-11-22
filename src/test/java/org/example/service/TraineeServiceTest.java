package org.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.example.dao.TraineeDAO;
import org.example.model.Trainee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TraineeServiceTest {

    private TraineeService traineeService;
    private TraineeDAO traineeDAOMock;

    @BeforeEach
    void setUp() {
        traineeDAOMock = mock(TraineeDAO.class);
        traineeService = new TraineeService(traineeDAOMock);
    }

    @Test
    void createTrainee() {
        Trainee sampleTrainee = new Trainee();
        sampleTrainee.setId(1L);
        sampleTrainee.setAddress("123 Main St");
        sampleTrainee.setDateOfBirth(new Date()); // Set an appropriate date
        sampleTrainee.setUserId(1L);

        when(traineeDAOMock.save(sampleTrainee)).thenReturn(sampleTrainee);
        Trainee returnTrainee = traineeService.createTrainee(sampleTrainee);

        verify(traineeDAOMock, times(1)).save(sampleTrainee);
        assertEquals(sampleTrainee, returnTrainee);
    }

    @Test
    void getTraineeById() {
        long traineeId = 1L;
        Trainee expectedTrainee = new Trainee();
        when(traineeDAOMock.findById(traineeId)).thenReturn(expectedTrainee);

        Trainee actualTrainee = traineeService.getTraineeById(traineeId);

        verify(traineeDAOMock, times(1)).findById(traineeId);
        assertEquals(expectedTrainee, actualTrainee);
    }

    @Test
    void updateTrainee() {
        Trainee sampleTrainee = new Trainee();
        sampleTrainee.setId(1L);
        sampleTrainee.setAddress("123 Main St");
        sampleTrainee.setDateOfBirth(new Date()); // Set an appropriate date
        sampleTrainee.setUserId(1L);

        when(traineeDAOMock.update(sampleTrainee)).thenReturn(sampleTrainee);

        Trainee returnTrainee = traineeService.updateTrainee(sampleTrainee);

        verify(traineeDAOMock, times(1)).update(sampleTrainee);
        assertEquals(sampleTrainee, returnTrainee);
    }

    @Test
    void deleteTrainee() {
        long traineeId = 1L;
        traineeService.deleteTrainee(traineeId);

        verify(traineeDAOMock, times(1)).delete(traineeId);
    }

    @Test
    void getAllTrainees() {
        Trainee sampleTrainee = new Trainee();
        sampleTrainee.setId(1L);
        sampleTrainee.setAddress("123 Main St");
        sampleTrainee.setDateOfBirth(new Date()); // Set an appropriate date
        sampleTrainee.setUserId(1L);

        List<Trainee> expectedTrainees = Collections.singletonList(sampleTrainee);
        when(traineeDAOMock.getAllTrainees()).thenReturn(expectedTrainees);

        List<Trainee> actualTrainees = traineeService.getAllTrainees();

        verify(traineeDAOMock, times(1)).getAllTrainees();
        assertEquals(expectedTrainees, actualTrainees);
    }
}