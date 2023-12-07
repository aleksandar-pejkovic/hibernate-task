package org.example.dao;

import java.util.List;
import java.util.Optional;

import org.example.model.Trainer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class TrainerDAO extends AbstractDAO {

    @Autowired
    public TrainerDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Trainer saveTrainer(Trainer trainer) {
        return (Trainer) save(trainer);
    }

    public Trainer findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query<Trainer> query = session.createQuery("FROM Trainer t where t.user.username = :username", Trainer.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

    public Trainer updateTrainer(Trainer trainer) {
        return (Trainer) update(trainer);
    }

    public boolean delete(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query<Long> subQuery = session.createQuery("DELETE FROM Trainer t WHERE t.user.username = :username",
                Long.class);
        subQuery.setParameter("username", username);

        Long trainerId = subQuery.uniqueResult();

        if (Optional.ofNullable(trainerId).isPresent()) {
            Trainer trainer = session.get(Trainer.class, trainerId);
            session.remove(trainer);
            log.info("Trainer deleted successfully. USERNAME: {}", username);
            return true;
        } else {
            log.error("Trainer not found for USERNAME: {}", username);
            return false;
        }
    }

    public List<Trainer> getNotAssigned(String traineeUsername) {
        Session session = sessionFactory.getCurrentSession();

        String hql = "SELECT t FROM Trainer t "
                + "LEFT JOIN t.traineeList te "
                + "WHERE te IS NULL OR te.user.username = :traineeUsername";

        Query<Trainer> query = session.createQuery(hql, Trainer.class);
        query.setParameter("traineeUsername", traineeUsername);

        List<Trainer> trainerList = query.getResultList();
        log.info("Successfully retrieved unassigned trainers list: {}", trainerList);
        return trainerList;
    }

    public List<Trainer> getAllTrainers() {
        return (List<Trainer>) findAll(Trainer.class);
    }
}
