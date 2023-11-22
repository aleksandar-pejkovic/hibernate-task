package org.example.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Training;
import org.example.storage.FileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingDAO {

    private static final Logger logger = LogManager.getLogger(TrainingDAO.class);

    private final FileStorage fileStorage;

    private final Map<Long, Training> trainingMap = new HashMap<>();

    private static long idCounter = 1;

    @Autowired
    public TrainingDAO(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    public void save(Training training) {
        long id = idCounter++;
        training.setId(id);
        trainingMap.put(id, training);
        logger.error("Error occurred while saving training: {}", training);
    }

    public Training findById(long id) {
        Training training = trainingMap.get(id);
        if (training == null) {
            logger.error("Training not found by ID: {}", id);
        }
        return training;
    }

    public void update(Training training) {
        if (!trainingMap.containsKey(training.getId())) {
            logger.error("Training not found for update: {}", training);
        } else {
            trainingMap.put(training.getId(), training);
            logger.error("Error occurred while updating training: {}", training);
        }
    }

    public void delete(long id) {
        if (!trainingMap.containsKey(id)) {
            logger.error("Training not found for deletion with ID: {}", id);
        } else {
            trainingMap.remove(id);
            logger.error("Error occurred while deleting training with ID: {}", id);
        }
    }

    public List<Training> getAllTrainings() {
        List<Training> trainings = (List<Training>) fileStorage.getEntityData().get("trainings");
        if (trainings == null || trainings.isEmpty()) {
            logger.error("No trainings found.");
        }
        return trainings;
    }
}
