package org.example.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Trainee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class TraineeDAO {

    private static final Logger logger = LogManager.getLogger(TraineeDAO.class);

    private final SessionFactory sessionFactory;

    @Autowired
    public TraineeDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Trainee trainee) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.persist(trainee);
        } catch (Exception e) {
            logger.error("Error while saving trainee", e);
            throw e;
        }
    }

    public Trainee findById(long id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(Trainee.class, id);
        } catch (Exception e) {
            logger.error("Error while finding trainee by ID: " + id, e);
            throw e;
        }
    }

    public Trainee update(Trainee trainee) {
        try {
            Session session = sessionFactory.getCurrentSession();
            return (Trainee) session.merge(trainee);
        } catch (Exception e) {
            logger.error("Error while updating trainee", e);
            throw e;
        }
    }

    public void delete(Trainee trainee) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.remove(trainee);
        } catch (Exception e) {
            logger.error("Error while deleting trainee", e);
            throw e;
        }
    }

    public List<Trainee> getAllTrainees() {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Trainee> query = session.createQuery("FROM Trainee", Trainee.class);
            return query.list();
        } catch (Exception e) {
            logger.error("Error while finding all trainees", e);
            throw e;
        }
    }
}
