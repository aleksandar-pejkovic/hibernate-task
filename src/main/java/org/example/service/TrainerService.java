package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dao.TrainerDAO;
import org.example.model.Trainer;
import org.example.utils.CredentialsGenerator;
import org.example.utils.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TrainerService {

    private static final Logger logger = LogManager.getLogger(TrainerService.class);

    private final TrainerDAO trainerDAO;

    private CredentialsGenerator generator;

    private UserAuthentication authentication;

    @Autowired
    public TrainerService(TrainerDAO trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    @Autowired
    public void setGenerator(CredentialsGenerator credentialsGenerator) {
        this.generator = credentialsGenerator;
    }

    @Autowired
    public void setAuthentication(UserAuthentication authentication) {
        this.authentication = authentication;
    }

    public void createTrainer(Trainer trainer) {
        String username = generator.generateUsername(trainer.getUser());
        String password = generator.generateRandomPassword();
        trainer.getUser().setUsername(username);
        trainer.getUser().setPassword(password);
        trainerDAO.save(trainer);
        logger.info("Trainer created: {}", trainer);
    }

    public Trainer getTrainerByUsername(String username) {
        Trainer trainer = trainerDAO.findByUsername(username);
        logger.info("Retrieved Trainer by USERNAME {}: {}", username, trainer);
        return trainer;
    }

    public Trainer changePassword(String username, String oldPassword, String newPassword) {
        authentication.authenticateUser(username, oldPassword);
        Trainer trainer = getTrainerByUsername(username);
        trainer.setPassword(newPassword);
        Trainer updatedTrainer = trainerDAO.update(trainer);
        logger.info("Password updated for trainer: {}", trainer);
        return updatedTrainer;
    }

    public void updateTrainer(Trainer trainer) {
        authentication.authenticateUser(trainer.getUsername(), trainer.getPassword());
        trainerDAO.update(trainer);
        logger.info("Trainer updated: {}", trainer);
    }

    public Trainer activateTrainer(Trainer trainer) {
        authentication.authenticateUser(trainer.getUsername(), trainer.getPassword());
        trainer.activateAccount();
        Trainer updatedTrainer = trainerDAO.update(trainer);
        logger.info("Activated account for trainer: {}", trainer);
        return updatedTrainer;
    }

    public Trainer deactivateTrainer(Trainer trainer) {
        authentication.authenticateUser(trainer.getUsername(), trainer.getPassword());
        trainer.deactivateAccount();
        Trainer updatedTrainer = trainerDAO.update(trainer);
        logger.info("Deactivated account for trainer: {}", trainer);
        return updatedTrainer;
    }

    public void deleteTrainer(String username, String password) {
        authentication.authenticateUser(username, password);
        logger.info("Deleting trainer with USERNAME: {}", username);
        trainerDAO.delete(username);
    }

    public List<Trainer> getNotAssignedTrainerList(String traineeUsername, String password) {
        authentication.authenticateUser(traineeUsername, password);
        logger.info("Retrieving trainer list for trainee with USERNAME: {}", traineeUsername);
        return trainerDAO.getNotAssigned(traineeUsername);
    }

    public List<Trainer> getAllTrainers() {
        List<Trainer> trainers = trainerDAO.getAllTrainers();
        logger.info("Retrieved all Trainers: {}", trainers);
        return trainers;
    }
}
