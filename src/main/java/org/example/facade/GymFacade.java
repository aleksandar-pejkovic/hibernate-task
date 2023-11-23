package org.example.facade;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.service.TraineeService;
import org.example.service.TrainerService;
import org.example.service.TrainingService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GymFacade {

    private static final Logger logger = LogManager.getLogger(GymFacade.class);

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;
    private final UserService userService;

    @Autowired
    public GymFacade(TraineeService traineeService,
                     TrainerService trainerService,
                     TrainingService trainingService,
                     UserService userService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
        this.userService = userService;
    }

    public void readTrainees() {
        logger.info("Reading trainees...");
        List<Trainee> trainees = traineeService.getAllTrainees();
    }

    public void readTrainers() {
        logger.info("Reading trainers...");
        List<Trainer> trainers = trainerService.getAllTrainers();
    }

    public void readTrainings() {
        logger.info("Reading trainings...");
        List<Training> trainings = trainingService.getAllTrainings();
    }

    public void createTrainee(Trainee trainee) {
        logger.info("Creating trainee...");
        traineeService.createTrainee(trainee);
    }

    public Trainee getTraineeById(Long traineeId) {
        logger.info("Getting trainee by ID: {}", traineeId);
        return traineeService.getTraineeById(traineeId);
    }

    public Trainee updateTrainee(Trainee trainee) {
        logger.info("Updating trainee...");
        return traineeService.updateTrainee(trainee);
    }

    public void deleteTrainee(Trainee trainee) {
        logger.info("Deleting trainee with ID: {}", trainee.getId());
        traineeService.deleteTrainee(trainee);
    }

    public void createTrainer(Trainer trainer) {
        logger.info("Creating trainer...");
        trainerService.createTrainer(trainer);
    }

    public Trainer getTrainerById(Long trainerId) {
        logger.info("Getting trainer by ID: {}", trainerId);
        return trainerService.getTrainerById(trainerId);
    }

    public void updateTrainer(Trainer trainer) {
        logger.info("Updating trainer...");
        trainerService.updateTrainer(trainer);
    }

    public void deleteTrainer(Trainer trainer) {
        logger.info("Deleting trainer with ID: {}", trainer.getId());
        trainerService.deleteTrainer(trainer);
    }

    public void createTraining(Training training) {
        logger.info("Creating training...");
        trainingService.createTraining(training);
    }

    public Training getTrainingById(Long trainingId) {
        logger.info("Getting training by ID: {}", trainingId);
        return trainingService.getTrainingById(trainingId);
    }

    public void updateTraining(Training training) {
        logger.info("Updating training...");
        trainingService.updateTraining(training);
    }

    public void deleteTraining(Training training) {
        logger.info("Deleting training with ID: {}", training.getId());
        trainingService.deleteTraining(training);
    }
}
