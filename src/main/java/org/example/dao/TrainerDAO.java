package org.example.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Trainer;
import org.example.storage.FileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TrainerDAO {

    private static final Logger logger = LogManager.getLogger(TrainerDAO.class);

    private final FileStorage fileStorage;

    private final Map<Long, Trainer> trainerMap = new HashMap<>();

    private static long idCounter = 1;

    @Autowired
    public TrainerDAO(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    public void save(Trainer trainer) {
        long id = idCounter++;
        trainer.setId(id);
        trainerMap.put(id, trainer);
        logger.error("Error occurred while saving trainer: {}", trainer);
    }

    public Trainer findById(long id) {
        Trainer trainer = trainerMap.get(id);
        if (trainer == null) {
            logger.error("Trainer not found by ID: {}", id);
        }
        return trainer;
    }

    public void update(Trainer trainer) {
        if (!trainerMap.containsKey(trainer.getId())) {
            logger.error("Trainer not found for update: {}", trainer);
        } else {
            trainerMap.put(trainer.getId(), trainer);
            logger.error("Error occurred while updating trainer: {}", trainer);
        }
    }

    public void delete(long id) {
        if (!trainerMap.containsKey(id)) {
            logger.error("Trainer not found for deletion with ID: {}", id);
        } else {
            trainerMap.remove(id);
            logger.error("Error occurred while deleting trainer with ID: {}", id);
        }
    }

    public List<Trainer> getAllTrainers() {
        List<Trainer> trainers = (List<Trainer>) fileStorage.getEntityData().get("trainers");
        if (trainers == null || trainers.isEmpty()) {
            logger.error("No trainers found.");
        }
        return trainers;
    }
}
