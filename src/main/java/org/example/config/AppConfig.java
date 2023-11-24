package org.example.config;

import org.example.dao.*;
import org.example.facade.GymFacade;
import org.example.service.TraineeService;
import org.example.service.TrainerService;
import org.example.service.TrainingService;
import org.example.utils.CredentialsGenerator;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.example")
public class AppConfig {

    @Bean
    public TraineeDAO traineeDAO(SessionFactory sessionFactory) {
        return new TraineeDAO(sessionFactory);
    }

    @Bean
    public TrainerDAO trainerDAO(SessionFactory sessionFactory) {
        return new TrainerDAO(sessionFactory);
    }

    @Bean
    public TrainingDAO trainingDAO(SessionFactory sessionFactory) {
        return new TrainingDAO(sessionFactory);
    }

    @Bean
    public UserDAO userDAO(SessionFactory sessionFactory) {
        return new UserDAO(sessionFactory);
    }

    @Bean
    public TrainingTypeDAO trainingTypeDAO(SessionFactory sessionFactory) {
        return new TrainingTypeDAO(sessionFactory);
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
    public CredentialsGenerator credentialsGenerator(SessionFactory sessionFactory) {
        return new CredentialsGenerator(sessionFactory);
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
