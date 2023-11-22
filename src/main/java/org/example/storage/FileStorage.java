package org.example.storage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
public class FileStorage implements Storage {

    @Getter
    private final Map<String, List<?>> entityData = new HashMap<>();

    private final Data data;

    @Override
    public Map<String, List<?>> getAllData() {
        return new HashMap<>(entityData);
    }

    @Autowired
    public FileStorage(Data data) {
        this.data = data;
        initData();
    }

    private void initData() {
        addEntities("trainees", data.getTrainees());
        addEntities("trainers", data.getTrainers());
        addEntities("trainings", data.getTrainings());
        addEntities("trainingTypes", data.getTrainingTypes());
        addEntities("users", data.getUsers());
    }

    private void addEntities(String entityType, List<?> entities) {
        entityData.put(entityType, entities);
    }
}
