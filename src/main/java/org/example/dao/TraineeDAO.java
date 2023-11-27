package org.example.dao;

import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Trainee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    public Trainee findByUsername(String username) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Trainee> query = session.createQuery("FROM Trainee t where t.user.username = :username",
                    Trainee.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (Exception e) {
            logger.error("Error while finding trainee by USERNAME: " + username, e);
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
