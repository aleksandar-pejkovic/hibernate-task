package org.example.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dao.TrainingDAO;
import org.example.model.Training;
import org.example.utils.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TrainingService {

    private static final Logger logger = LogManager.getLogger(TrainingService.class);

    private final TrainingDAO trainingDAO;

    private UserAuthentication authentication;

    @Autowired
    public TrainingService(TrainingDAO trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    @Autowired
    public void setAuthentication(UserAuthentication authentication) {
        this.authentication = authentication;
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

    public Training updateTraining(Training training) {
        Training updatedTraining = trainingDAO.update(training);
        logger.info("Training updated: {}", training);
        return updatedTraining;
    }

    public void deleteTraining(Training training) {
        trainingDAO.delete(training);
        logger.info("Training deleted with ID: {}", training.getId());
    }

    public List<Training> getTraineeTrainingList(String username, String password, int trainingDuration) {
        authentication.authenticateUser(username, password);
        logger.info("Retrieving training list for trainee with USERNAME: {}", username);
        List<Training> trainingList = trainingDAO.getTraineeTrainingList(username, trainingDuration);
        logger.info("Successfully retrieved training list: {}", trainingList);
        return trainingList;
    }

    public List<Training> getTrainerTrainingList(String username, String password, int trainingDuration) {
        authentication.authenticateUser(username, password);
        logger.info("Retrieving training list for trainer with USERNAME: {}", username);
        List<Training> trainingList = trainingDAO.getTrainerTrainingList(username, trainingDuration);
        logger.info("Successfully retrieved training list: {}", trainingList);
        return trainingList;
    }

    public List<Training> getAllTrainings() {
        logger.info("Reading trainings...");
        List<Training> trainings = trainingDAO.getAllTrainings();
        logger.info("Retrieved all Trainings: {}", trainings);
        return trainings;
    }
}
