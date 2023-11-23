package org.example.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.TrainingType;
import org.example.storage.FileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TrainingTypeDAO {

    private static final Logger logger = LogManager.getLogger(TrainingTypeDAO.class);

    private final FileStorage fileStorage;

    private final Map<Long, TrainingType> trainingTypeMap = new HashMap<>();

    private static long idCounter = 1;

    @Autowired
    public TrainingTypeDAO(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    public void save(TrainingType trainingType) {
        long id = idCounter++;
        trainingType.setId(id);
        trainingTypeMap.put(id, trainingType);
    }

    public TrainingType findById(long id) {
        TrainingType trainingType = trainingTypeMap.get(id);
        if (trainingType == null) {
            logger.error("Training type not found by ID: {}", id);
        }
        return trainingType;
    }

    public void update(TrainingType trainingType) {
        if (!trainingTypeMap.containsKey(trainingType.getId())) {
            logger.error("Training type not found for update: {}", trainingType);
        } else {
            trainingTypeMap.put(trainingType.getId(), trainingType);
        }
    }

    public void delete(long id) {
        if (!trainingTypeMap.containsKey(id)) {
            logger.error("Training type not found for deletion with ID: {}", id);
        } else {
            trainingTypeMap.remove(id);
        }
    }

    public List<TrainingType> getAllTrainingTypes() {
        List<TrainingType> trainingTypes = (List<TrainingType>) fileStorage.getEntityData().get("trainingTypes");
        if (trainingTypes == null || trainingTypes.isEmpty()) {
            logger.error("No training types found.");
        }
        return trainingTypes;
    }
}
