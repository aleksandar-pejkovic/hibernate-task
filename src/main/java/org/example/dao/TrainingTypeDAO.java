package org.example.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.TrainingType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainingTypeDAO {

    private static final Logger logger = LogManager.getLogger(TrainingTypeDAO.class);

    private final SessionFactory sessionFactory;

    @Autowired
    public TrainingTypeDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(TrainingType trainingType) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.persist(trainingType);
            transaction.commit();
            logger.info("TrainingType saved successfully. ID: {}", trainingType.getId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while saving trainingType", e);
        } finally {
            session.close();
        }
    }

    public TrainingType findById(long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        TrainingType trainingType = null;

        try {
            transaction = session.beginTransaction();
            trainingType = session.get(TrainingType.class, id);
            transaction.commit();
            if (trainingType == null) {
                logger.error("TrainingType not found by ID: {}", id);
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while finding trainingType by ID: " + id, e);
        } finally {
            session.close();
        }

        return trainingType;
    }

    public TrainingType update(TrainingType trainingType) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            TrainingType updatedTrainingType = (TrainingType) session.merge(trainingType);
            transaction.commit();
            logger.info("TrainingType updated successfully. ID: {}", updatedTrainingType.getId());
            return updatedTrainingType;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while updating trainingType", e);
            return null;
        } finally {
            session.close();
        }
    }

    public void delete(TrainingType trainingType) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.remove(trainingType);
            transaction.commit();
            logger.info("TrainingType deleted successfully. ID: {}", trainingType.getId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while deleting trainingType", e);
        } finally {
            session.close();
        }
    }

    public List<TrainingType> getAllTrainingTypes() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<TrainingType> trainingTypeList = null;

        try {
            transaction = session.beginTransaction();
            Query<TrainingType> query = session.createQuery("FROM TrainingType", TrainingType.class);
            trainingTypeList = query.list();
            transaction.commit();
            logger.info("Retrieved all trainingTypes. Count: {}", trainingTypeList.size());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while finding all trainingTypes", e);
        } finally {
            session.close();
        }

        return trainingTypeList;
    }
}
