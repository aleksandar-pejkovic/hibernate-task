package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.dao.TraineeDAO;
import org.example.model.Trainee;
import org.example.utils.CredentialsGenerator;
import org.example.utils.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class TraineeService {

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

    @Transactional
    public void createTrainee(Trainee trainee) {
        String username = generator.generateUsername(trainee.getUser());
        String password = generator.generateRandomPassword();
        trainee.getUser().setUsername(username);
        trainee.getUser().setPassword(password);
        traineeDAO.save(trainee);
        log.info("Trainee created: {}", trainee);
    }

    @Transactional(readOnly = true)
    public Trainee getTraineeByUsername(String username) {
        Trainee trainee = traineeDAO.findByUsername(username);
        log.info("Retrieved Trainee by USERNAME {}: {}", username, trainee);
        return trainee;
    }

    @Transactional
    public Trainee changePassword(String username, String oldPassword, String newPassword) {
        authentication.authenticateUser(username, oldPassword);
        Trainee trainee = getTraineeByUsername(username);
        trainee.setPassword(newPassword);
        Trainee updatedTrainee = traineeDAO.update(trainee);
        log.info("Password updated for trainee: {}", trainee);
        return updatedTrainee;
    }

    @Transactional
    public Trainee updateTrainee(Trainee trainee) {
        authentication.authenticateUser(trainee.getUsername(), trainee.getPassword());
        Trainee updatedTrainee = traineeDAO.update(trainee);
        log.info("Trainee updated: {}", trainee);
        return updatedTrainee;
    }

    @Transactional
    public Trainee activateTrainee(Trainee trainee) {
        authentication.authenticateUser(trainee.getUsername(), trainee.getPassword());
        trainee.activateAccount();
        Trainee updatedTrainee = traineeDAO.update(trainee);
        log.info("Activated account for trainee: {}", trainee);
        return updatedTrainee;
    }

    @Transactional
    public Trainee deactivateTrainee(Trainee trainee) {
        authentication.authenticateUser(trainee.getUsername(), trainee.getPassword());
        trainee.deactivateAccount();
        Trainee updatedTrainee = traineeDAO.update(trainee);
        log.info("Deactivated account for trainee: {}", trainee);
        return updatedTrainee;
    }

    @Transactional
    public boolean deleteTrainee(String username, String password) {
        authentication.authenticateUser(username, password);
        return traineeDAO.delete(username);
    }

    @Transactional(readOnly = true)
    public List<Trainee> getAllTrainees() {
        List<Trainee> trainees = traineeDAO.getAllTrainees();
        log.info("Retrieved all Trainees: {}", trainees);
        return trainees;
    }
}
