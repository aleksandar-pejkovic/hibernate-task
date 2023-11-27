package org.example.dao;

import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Trainer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class TrainerDAO {

    private static final Logger logger = LogManager.getLogger(TrainerDAO.class);

    private final SessionFactory sessionFactory;

    @Autowired
    public TrainerDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Trainer trainer) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(trainer);
        logger.info("Trainer saved successfully. ID: {}", trainer.getId());
    }

    public Trainer findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query<Trainer> query = session.createQuery("FROM Trainer t where t.user.username = :username", Trainer.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

    public Trainer update(Trainer trainer) {
        Session session = sessionFactory.getCurrentSession();
        return (Trainer) session.merge(trainer);
    }

    public void delete(Trainer trainer) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(trainer);
        logger.info("Trainer deleted successfully. ID: {}", trainer.getId());
    }

    public List<Trainer> getAllTrainers() {
        Session session = sessionFactory.getCurrentSession();
        Query<Trainer> query = session.createQuery("FROM Trainer", Trainer.class);
        List<Trainer> trainerList = query.list();
        logger.info("Retrieved all trainers. Count: {}", trainerList.size());
        return trainerList;
    }
}
