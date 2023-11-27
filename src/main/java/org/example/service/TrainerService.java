package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dao.TrainerDAO;
import org.example.model.Trainer;
import org.example.utils.CredentialsGenerator;
import org.example.utils.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public void updateTrainer(Trainer trainer) {
        authentication.authenticateUser(trainer.getUser());
        trainerDAO.update(trainer);
        logger.info("Trainer updated: {}", trainer);
    }

    public void deleteTrainer(Trainer trainer) {
        authentication.authenticateUser(trainer.getUser());
        trainerDAO.delete(trainer);
        logger.info("Trainer deleted with ID: {}", trainer.getId());
    }

    public List<Trainer> getAllTrainers() {
        List<Trainer> trainers = trainerDAO.getAllTrainers();
        logger.info("Retrieved all Trainers: {}", trainers);
        return trainers;
    }
}
