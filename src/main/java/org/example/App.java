package org.example;

import org.example.config.AppConfig;
import org.example.config.HibernateConfig;
import org.example.facade.GymFacade;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class,
                HibernateConfig.class);

        GymFacade gymFacade = context.getBean(GymFacade.class);
        gymFacade.readTrainees();
        gymFacade.readTrainers();
        gymFacade.readTrainings();

        context.close();
    }
}
