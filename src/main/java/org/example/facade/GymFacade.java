package org.example.facade;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Trainee;
import org.example.model.Trainer;
import org.example.model.Training;
import org.example.service.TraineeService;
import org.example.service.TrainerService;
import org.example.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GymFacade {

    private static final Logger logger = LogManager.getLogger(GymFacade.class);

    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    @Autowired
    public GymFacade(TraineeService traineeService,
                     TrainerService trainerService,
                     TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    // Trainee-related methods

    public void readTrainees() {
        logger.info("Reading trainees...");
        List<Trainee> trainees = traineeService.getAllTrainees();
    }

    public void createTrainee(Trainee trainee) {
        logger.info("Creating trainee...");
        traineeService.createTrainee(trainee);
    }

    public Trainee getTraineeByUsername(String username) {
        logger.info("Getting trainee by USERNAME: {}", username);
        return traineeService.getTraineeByUsername(username);
    }

    public Trainee changeTraineePassword(String username, String oldPassword, String newPassword) {
        logger.info("Changing trainee password...");
        return traineeService.changePassword(username, oldPassword, newPassword);
    }

    public Trainee updateTrainee(Trainee trainee) {
        logger.info("Updating trainee...");
        return traineeService.updateTrainee(trainee);
    }

    public Trainee activateTrainee(Trainee trainee) {
        logger.info("Activating trainee...");
        return traineeService.activateTrainee(trainee);
    }

    public Trainee deactivateTrainee(Trainee trainee) {
        logger.info("Deactivating trainee...");
        return traineeService.deactivateTrainee(trainee);
    }

    public void deleteTrainee(Trainee trainee) {
        logger.info("Deleting trainee with ID: {}", trainee.getId());
        traineeService.deleteTrainee(trainee);
    }

    // Trainer-related methods

    public void readTrainers() {
        logger.info("Reading trainers...");
        List<Trainer> trainers = trainerService.getAllTrainers();
    }

    public void createTrainer(Trainer trainer) {
        logger.info("Creating trainer...");
        trainerService.createTrainer(trainer);
    }

    public Trainer getTrainerUsername(String username) {
        logger.info("Getting trainer by Username: {}", username);
        return trainerService.getTrainerByUsername(username);
    }

    public Trainer changeTrainerPassword(String username, String oldPassword, String newPassword) {
        logger.info("Changing trainer password...");
        return trainerService.changePassword(username, oldPassword, newPassword);
    }

    public void updateTrainer(Trainer trainer) {
        logger.info("Updating trainer...");
        trainerService.updateTrainer(trainer);
    }

    public Trainer activateTrainer(Trainer trainer) {
        logger.info("Activating trainer...");
        return trainerService.activateTrainer(trainer);
    }

    public Trainer deactivateTrainer(Trainer trainer) {
        logger.info("Deactivating trainer...");
        return trainerService.deactivateTrainer(trainer);
    }

    public void deleteTrainer(Trainer trainer) {
        logger.info("Deleting trainer with ID: {}", trainer.getId());
        trainerService.deleteTrainer(trainer);
    }

    // Training-related methods

    public void readTrainings() {
        logger.info("Reading trainings...");
        List<Training> trainings = trainingService.getAllTrainings();
    }

    public void createTraining(Training training) {
        logger.info("Creating training...");
        trainingService.createTraining(training);
    }

    public Training getTrainingById(Long trainingId) {
        logger.info("Getting training by ID: {}", trainingId);
        return trainingService.getTrainingById(trainingId);
    }

    public Training updateTraining(Training training) {
        logger.info("Updating training...");
        return trainingService.updateTraining(training);
    }

    public void deleteTraining(Training training) {
        logger.info("Deleting training with ID: {}", training.getId());
        trainingService.deleteTraining(training);
    }
}
