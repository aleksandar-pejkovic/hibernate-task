package org.example.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Trainee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TraineeDAO {

    private static final Logger logger = LogManager.getLogger(TraineeDAO.class);

    private final SessionFactory sessionFactory;

    @Autowired
    public TraineeDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Trainee trainee) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Trainee savedTrainee = null;
        try {
            transaction = session.beginTransaction();
            session.persist(trainee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while saving trainee", e);
        } finally {
            session.close();
        }
    }

    public Trainee findById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Trainee trainee = null;
        try {
            transaction = session.beginTransaction();
            trainee = session.get(Trainee.class, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while finding trainee by ID: " + id, e);
        } finally {
            session.close();
        }
        return trainee;
    }

    public Trainee update(Trainee trainee) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Trainee updatedTrainee = null;
        try {
            transaction = session.beginTransaction();
            updatedTrainee = session.merge(trainee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while updating trainee", e);
        } finally {
            session.close();
        }
        return updatedTrainee;
    }

    public void delete(Trainee trainee) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.remove(trainee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while deleting trainee", e);
        } finally {
            session.close();
        }
    }

    public List<Trainee> getAllTrainees() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Trainee> traineeList = null;

        try {
            transaction = session.beginTransaction();
            Query<Trainee> query = session.createQuery("FROM Trainee", Trainee.class);
            traineeList = query.list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while finding all trainees", e);
        } finally {
            session.close();
        }

        return traineeList;
    }
}
