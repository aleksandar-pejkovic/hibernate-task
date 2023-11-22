package org.example.config;

import org.example.dao.TraineeDAO;
import org.example.dao.TrainerDAO;
import org.example.dao.TrainingDAO;
import org.example.facade.GymFacade;
import org.example.service.TraineeService;
import org.example.service.TrainerService;
import org.example.service.TrainingService;
import org.example.storage.Data;
import org.example.storage.FileStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.example")
public class AppConfig {

    @Bean
    public Data data() {
        return new Data();
    }

    @Bean
    public TraineeDAO traineeDAO(FileStorage fileStorage) {
        return new TraineeDAO(fileStorage);
    }

    @Bean
    public TrainerDAO trainerDAO(FileStorage fileStorage) {
        return new TrainerDAO(fileStorage);
    }

    @Bean
    public TrainingDAO trainingDAO(FileStorage fileStorage) {
        return new TrainingDAO(fileStorage);
    }

    @Bean
    public TraineeService traineeService(TraineeDAO traineeDAO) {
        return new TraineeService(traineeDAO);
    }

    @Bean
    public TrainerService trainerService(TrainerDAO trainerDAO) {
        return new TrainerService(trainerDAO);
    }

    @Bean
    public TrainingService trainingService(TrainingDAO trainingDAO) {
        return new TrainingService(trainingDAO);
    }

    @Bean
    public GymFacade gymFacade(
            TraineeService traineeService,
            TrainerService trainerService,
            TrainingService trainingService
    ) {
        return new GymFacade(traineeService, trainerService, trainingService);
    }
}
