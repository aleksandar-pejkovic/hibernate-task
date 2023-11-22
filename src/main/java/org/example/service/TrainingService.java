package org.example.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dao.TrainingDAO;
import org.example.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingService {

    private static final Logger logger = LogManager.getLogger(TrainingService.class);

    private final TrainingDAO trainingDAO;

    @Autowired
    public TrainingService(TrainingDAO trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    public void createTraining(Training training) {
        trainingDAO.save(training);
        logger.info("Training created: {}", training);
    }

    public Training getTrainingById(long id) {
        Training training = trainingDAO.findById(id);
        logger.info("Retrieved Training by ID {}: {}", id, training);
        return training;
    }

    public void updateTraining(Training training) {
        trainingDAO.update(training);
        logger.info("Training updated: {}", training);
    }

    public void deleteTraining(long id) {
        trainingDAO.delete(id);
        logger.info("Training deleted with ID: {}", id);
    }

    public List<Training> getAllTrainings() {
        List<Training> trainings = trainingDAO.getAllTrainings();
        logger.info("Retrieved all Trainings: {}", trainings);
        return trainings;
    }
}
