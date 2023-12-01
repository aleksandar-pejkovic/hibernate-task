package org.example.dao;

import lombok.extern.slf4j.Slf4j;
import org.example.model.Trainer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class TrainerDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public TrainerDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Trainer trainer) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(trainer);
        log.info("Trainer saved successfully. ID: {}", trainer.getId());
    }

    public Trainer findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query<Trainer> query = session.createQuery("FROM Trainer t where t.user.username = :username", Trainer.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

    public Trainer update(Trainer trainer) {
        Session session = sessionFactory.getCurrentSession();
        return session.merge(trainer);
    }

    public void delete(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query<Long> subQuery = session.createQuery("SELECT t.id FROM Trainer t WHERE t.user.username = :username",
                Long.class);
        subQuery.setParameter("username", username);

        Long trainerId = subQuery.uniqueResult();

        if (trainerId != null) {
            Trainer trainer = session.get(Trainer.class, trainerId);
            session.remove(trainer);
            log.info("Trainer deleted successfully. USERNAME: {}", username);
        } else {
            log.error("Trainer not found for USERNAME: {}", username);
        }
    }

    public List<Trainer> getNotAssigned(String traineeUsername) {
        Session session = sessionFactory.getCurrentSession();

        String hql = "SELECT t FROM Trainer t WHERE t.id NOT IN " +
                "(SELECT tr.id FROM Trainee te JOIN te.trainerList tr WHERE te.user.username = :username)";

        Query<Trainer> query = session.createQuery(hql, Trainer.class);
        query.setParameter("username", traineeUsername);

        List<Trainer> trainerList = query.getResultList();
        log.info("Successfully retrieved unassigned trainers list: {}", trainerList);
        return trainerList;
    }

    public List<Trainer> getAllTrainers() {
        Session session = sessionFactory.getCurrentSession();
        Query<Trainer> query = session.createQuery("FROM Trainer", Trainer.class);
        List<Trainer> trainerList = query.list();
        log.info("Retrieved all trainers. Count: {}", trainerList.size());
        return trainerList;
    }
}
