package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.dao.TrainerDAO;
import org.example.model.Trainer;
import org.example.utils.CredentialsGenerator;
import org.example.utils.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class TrainerService {

    private final TrainerDAO trainerDAO;

    private final CredentialsGenerator generator;

    private final UserAuthentication authentication;

    @Autowired
    public TrainerService(TrainerDAO trainerDAO, CredentialsGenerator credentialsGenerator, UserAuthentication authentication) {
        this.trainerDAO = trainerDAO;
        this.generator = credentialsGenerator;
        this.authentication = authentication;
    }

    @Transactional
    public void createTrainer(Trainer trainer) {
        String username = generator.generateUsername(trainer.getUser());
        String password = generator.generateRandomPassword();
        trainer.getUser().setUsername(username);
        trainer.getUser().setPassword(password);
        trainerDAO.save(trainer);
        log.info("Trainer created: {}", trainer);
    }

    @Transactional(readOnly = true)
    public Trainer getTrainerByUsername(String username) {
        Trainer trainer = trainerDAO.findByUsername(username);
        log.info("Retrieved Trainer by USERNAME {}: {}", username, trainer);
        return trainer;
    }

    @Transactional
    public Trainer changePassword(String username, String oldPassword, String newPassword) {
        authentication.authenticateUser(username, oldPassword);
        Trainer trainer = getTrainerByUsername(username);
        trainer.setPassword(newPassword);
        Trainer updatedTrainer = trainerDAO.update(trainer);
        log.info("Password updated for trainer: {}", trainer);
        return updatedTrainer;
    }

    @Transactional
    public void updateTrainer(Trainer trainer) {
        authentication.authenticateUser(trainer.getUsername(), trainer.getPassword());
        trainerDAO.update(trainer);
        log.info("Trainer updated: {}", trainer);
    }

    @Transactional
    public Trainer activateTrainer(Trainer trainer) {
        authentication.authenticateUser(trainer.getUsername(), trainer.getPassword());
        trainer.activateAccount();
        Trainer updatedTrainer = trainerDAO.update(trainer);
        log.info("Activated account for trainer: {}", trainer);
        return updatedTrainer;
    }

    @Transactional
    public Trainer deactivateTrainer(Trainer trainer) {
        authentication.authenticateUser(trainer.getUsername(), trainer.getPassword());
        trainer.deactivateAccount();
        Trainer updatedTrainer = trainerDAO.update(trainer);
        log.info("Deactivated account for trainer: {}", trainer);
        return updatedTrainer;
    }

    @Transactional
    public boolean deleteTrainer(String username, String password) {
        authentication.authenticateUser(username, password);
        log.info("Deleting trainer with USERNAME: {}", username);
        return trainerDAO.delete(username);
    }

    @Transactional(readOnly = true)
    public List<Trainer> getNotAssignedTrainerList(String traineeUsername, String password) {
        authentication.authenticateUser(traineeUsername, password);
        log.info("Retrieving trainer list for trainee with USERNAME: {}", traineeUsername);
        return trainerDAO.getNotAssigned(traineeUsername);
    }

    @Transactional(readOnly = true)
    public List<Trainer> getAllTrainers() {
        List<Trainer> trainers = trainerDAO.getAllTrainers();
        log.info("Retrieved all Trainers: {}", trainers);
        return trainers;
    }
}
