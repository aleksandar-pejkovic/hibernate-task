package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dao.TraineeDAO;
import org.example.model.Trainee;
import org.example.utils.CredentialsGenerator;
import org.example.utils.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeService {

    private static final Logger logger = LogManager.getLogger(TraineeService.class);

    private final TraineeDAO traineeDAO;

    private CredentialsGenerator generator;

    private UserAuthentication authentication;

    @Autowired
    public TraineeService(TraineeDAO traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

    @Autowired
    public void setGenerator(CredentialsGenerator credentialsGenerator) {
        this.generator = credentialsGenerator;
    }

    @Autowired
    public void setAuthentication(UserAuthentication authentication) {
        this.authentication = authentication;
    }

    public void createTrainee(Trainee trainee) {
        String username = generator.generateUsername(trainee.getUser());
        String password = generator.generateRandomPassword();
        trainee.getUser().setUsername(username);
        trainee.getUser().setPassword(password);
        traineeDAO.save(trainee);
        logger.info("Trainee created: {}", trainee);
    }

    public Trainee getTraineeByUsername(String username) {
        Trainee trainee = traineeDAO.findByUsername(username);
        logger.info("Retrieved Trainee by USERNAME {}: {}", username, trainee);
        return trainee;
    }

    public Trainee changePassword(String username, String oldPassword, String newPassword) {
        authentication.authenticateUser(username, oldPassword);
        Trainee trainee = getTraineeByUsername(username);
        trainee.setPassword(newPassword);
        Trainee updatedTrainee = traineeDAO.update(trainee);
        logger.info("Password updated for trainee: {}", trainee);
        return updatedTrainee;
    }

    public Trainee updateTrainee(Trainee trainee) {
        authentication.authenticateUser(trainee.getUsername(), trainee.getPassword());
        Trainee updatedTrainee = traineeDAO.update(trainee);
        logger.info("Trainee updated: {}", trainee);
        return updatedTrainee;
    }

    public Trainee activateTrainee(Trainee trainee) {
        authentication.authenticateUser(trainee.getUsername(), trainee.getPassword());
        trainee.activateAccount();
        Trainee updatedTrainee = traineeDAO.update(trainee);
        logger.info("Activated account for trainee: {}", trainee);
        return updatedTrainee;
    }

    public Trainee deactivateTrainee(Trainee trainee) {
        authentication.authenticateUser(trainee.getUsername(), trainee.getPassword());
        trainee.deactivateAccount();
        Trainee updatedTrainee = traineeDAO.update(trainee);
        logger.info("Deactivated account for trainee: {}", trainee);
        return updatedTrainee;
    }

    public void deleteTrainee(Trainee trainee) {
        authentication.authenticateUser(trainee.getUsername(), trainee.getPassword());
        traineeDAO.delete(trainee);
        logger.info("Trainee deleted with ID: {}", trainee.getId());
    }

    public List<Trainee> getAllTrainees() {
        List<Trainee> trainees = traineeDAO.getAllTrainees();
        logger.info("Retrieved all Trainees: {}", trainees);
        return trainees;
    }
}
