package org.example.storage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.model.TrainingType;
import org.example.model.User;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class Data {

    private final List<Trainee> trainees = new ArrayList<>();
    private final List<Trainer> trainers = new ArrayList<>();
    private final List<Training> trainings = new ArrayList<>();
    private final List<TrainingType> trainingTypes = new ArrayList<>();
    private final List<User> users = new ArrayList<>();

    public Data() {
        initDummyData();
    }

    private void initDummyData() {
        // Dummy Trainee entities
        trainees.add(new Trainee(1L, new Date(1990 - 1900, Calendar.MAY, 12), "123 Main St", 1L));
        trainees.add(new Trainee(2L, new Date(1992 - 1900, Calendar.FEBRUARY, 15), "456 Oak St", 2L));

        // Dummy Trainer entities
        trainers.add(new Trainer(1L, 1L, 1L));
        trainers.add(new Trainer(2L, 2L, 2L));

        // Dummy Training entities
        trainings.add(new Training(1L, 1L, 1L, "Training1", 1L, new Date(), 60));
        trainings.add(new Training(2L, 2L, 2L, "Training2", 2L, new Date(), 45));

        // Dummy TrainingType entities
        trainingTypes.add(new TrainingType(1L, "Cardio"));
        trainingTypes.add(new TrainingType(2L, "Strength"));

        // Dummy User entities
        users.add(new User(1L, "John", "Doe", "JohnDoe", "password1", true));
        users.add(new User(2L, "Jane", "Smith", "JaneSmith", "password2", true));
    }
}
