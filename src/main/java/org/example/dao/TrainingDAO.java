package org.example.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Training;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainingDAO {

    private static final Logger logger = LogManager.getLogger(TrainingDAO.class);

    private final SessionFactory sessionFactory;

    @Autowired
    public TrainingDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Training training) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.persist(training);
            transaction.commit();
            logger.info("Training saved successfully. ID: {}", training.getId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while saving training", e);
        } finally {
            session.close();
        }
    }

    public Training findById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        Training training = null;

        try {
            transaction = session.beginTransaction();
            training = session.get(Training.class, id);
            transaction.commit();
            if (training == null) {
                logger.error("Training not found by ID: {}", id);
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while finding training by ID: " + id, e);
        } finally {
            session.close();
        }

        return training;
    }

    public Training update(Training training) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Training updatedTraining = (Training) session.merge(training);
            transaction.commit();
            logger.info("Training updated successfully. ID: {}", updatedTraining.getId());
            return updatedTraining;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while updating training", e);
            return null;
        } finally {
            session.close();
        }
    }

    public void delete(Training training) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.remove(training);
            transaction.commit();
            logger.info("Training deleted successfully. ID: {}", training.getId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while deleting training", e);
        } finally {
            session.close();
        }
    }

    public List<Training> getAllTrainings() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Training> trainingList = null;

        try {
            transaction = session.beginTransaction();
            Query<Training> query = session.createQuery("FROM Training", Training.class);
            trainingList = query.list();
            transaction.commit();
            logger.info("Retrieved all trainings. Count: {}", trainingList.size());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while finding all trainings", e);
        } finally {
            session.close();
        }

        return trainingList;
    }
}
