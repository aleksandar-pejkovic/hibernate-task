package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.dao.TrainingDAO;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.utils.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class TrainingService {

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

    @Transactional
    public void createTraining(Training training) {
        Trainee trainee = training.getTrainee();
        Trainer trainer = training.getTrainer();
        trainee.addTrainer(trainer);
        trainingDAO.save(training);
        log.info("Training created: {}", training);
    }

    @Transactional(readOnly = true)
    public Training getTrainingById(long id) {
        Training training = trainingDAO.findById(id);
        log.info("Retrieved Training by ID {}: {}", id, training);
        return training;
    }

    @Transactional
    public Training updateTraining(Training training) {
        Training updatedTraining = trainingDAO.update(training);
        log.info("Training updated: {}", training);
        return updatedTraining;
    }

    @Transactional
    public boolean deleteTraining(Training training) {
        Trainee trainee = training.getTrainee();
        Trainer trainer = training.getTrainer();
        trainee.removeTrainer(trainer);
        trainingDAO.delete(training);
        log.info("Training deleted with ID: {}", training.getId());
        return true;
    }

    @Transactional(readOnly = true)
    public List<Training> getTraineeTrainingList(String username, String password, int trainingDuration) {
        authentication.authenticateUser(username, password);
        log.info("Retrieving training list for trainee with USERNAME: {}", username);
        List<Training> trainingList = trainingDAO.getTraineeTrainingList(username, trainingDuration);
        log.info("Successfully retrieved training list: {}", trainingList);
        return trainingList;
    }

    @Transactional(readOnly = true)
    public List<Training> getTrainerTrainingList(String username, String password, int trainingDuration) {
        authentication.authenticateUser(username, password);
        log.info("Retrieving training list for trainer with USERNAME: {}", username);
        List<Training> trainingList = trainingDAO.getTrainerTrainingList(username, trainingDuration);
        log.info("Successfully retrieved training list: {}", trainingList);
        return trainingList;
    }

    @Transactional(readOnly = true)
    public List<Training> getAllTrainings() {
        log.info("Reading trainings...");
        List<Training> trainings = trainingDAO.getAllTrainings();
        log.info("Retrieved all Trainings: {}", trainings);
        return trainings;
    }
}
