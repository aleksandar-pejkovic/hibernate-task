package org.example;

import java.util.Date;

import org.example.config.AppConfig;
import org.example.config.HibernateConfig;
import org.example.facade.GymFacade;
import org.example.model.Trainee;
import org.example.model.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class,
                HibernateConfig.class);

        User user = User.builder()
                .isActive(true)
                .lastName("Pejko")
                .firstName("Alex")
                .build();

        Trainee trainee = Trainee.builder()
                .user(user)
                .address("Ibrez bb")
                .dateOfBirth(new Date())
                .build();

        GymFacade gymFacade = context.getBean(GymFacade.class);
        gymFacade.createTrainee(trainee);
        gymFacade.readTrainees();
        gymFacade.readTrainers();
        gymFacade.readTrainings();

        context.close();
    }
}
