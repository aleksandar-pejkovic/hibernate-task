package org.example.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dao.TrainerDAO;
import org.example.model.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerService {

    private static final Logger logger = LogManager.getLogger(TrainerService.class);

    private final TrainerDAO trainerDAO;

    @Autowired
    public TrainerService(TrainerDAO trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    public void createTrainer(Trainer trainer) {
        trainerDAO.save(trainer);
        logger.info("Trainer created: {}", trainer);
    }

    public Trainer getTrainerById(long id) {
        Trainer trainer = trainerDAO.findById(id);
        logger.info("Retrieved Trainer by ID {}: {}", id, trainer);
        return trainer;
    }

    public void updateTrainer(Trainer trainer) {
        trainerDAO.update(trainer);
        logger.info("Trainer updated: {}", trainer);
    }

    public void deleteTrainer(long id) {
        trainerDAO.delete(id);
        logger.info("Trainer deleted with ID: {}", id);
    }

    public List<Trainer> getAllTrainers() {
        List<Trainer> trainers = trainerDAO.getAllTrainers();
        logger.info("Retrieved all Trainers: {}", trainers);
        return trainers;
    }
}
