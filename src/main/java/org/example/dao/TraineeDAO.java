package org.example.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Trainee;
import org.example.storage.FileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TraineeDAO {

    private static final Logger logger = LogManager.getLogger(TraineeDAO.class);

    private final FileStorage fileStorage;

    private final Map<Long, Trainee> traineeMap = new HashMap<>();

    private static long idCounter = 3;

    @Autowired
    public TraineeDAO(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    public Trainee save(Trainee trainee) {
        long id = idCounter++;
        trainee.setId(id);
        return traineeMap.put(id, trainee);
    }

    public Trainee findById(long id) {
        Trainee trainee = traineeMap.get(id);
        if (trainee == null) {
            logger.error("Trainee not found by ID: {}", id);
        }
        return trainee;
    }

    public Trainee update(Trainee trainee) {
        if (!traineeMap.containsKey(trainee.getId())) {
            logger.error("Trainee not found for update: {}", trainee);
        }
        return traineeMap.put(trainee.getId(), trainee);
    }

    public void delete(long id) {
        if (!traineeMap.containsKey(id)) {
            logger.error("Trainee not found for deletion with ID: {}", id);
        } else {
            traineeMap.remove(id);
            logger.error("Error occurred while deleting trainee with ID: {}", id);
        }
    }

    public List<Trainee> getAllTrainees() {
        List<Trainee> trainees = (List<Trainee>) fileStorage.getEntityData().get("trainees");
        if (trainees == null || trainees.isEmpty()) {
            logger.error("No trainees found.");
        }
        return trainees;
    }
}
