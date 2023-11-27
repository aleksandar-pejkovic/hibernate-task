package org.example.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Trainer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainerDAO {

    private static final Logger logger = LogManager.getLogger(TrainerDAO.class);

    private final SessionFactory sessionFactory;

    @Autowired
    public TrainerDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Trainer trainer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.persist(trainer);
            transaction.commit();
            logger.info("Trainer saved successfully. ID: {}", trainer.getId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while saving trainer", e);
        } finally {
            session.close();
        }
    }

    public Trainer findByUsername(String username) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Trainer> query = session.createQuery("FROM Trainer t where t.user.username = :username",
                    Trainer.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (Exception e) {
            logger.error("Error while finding trainer by USERNAME: " + username, e);
            throw e;
        }
    }

    public void update(Trainer trainer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Trainer updatedTrainer = (Trainer) session.merge(trainer);
            transaction.commit();
            logger.info("Trainer updated successfully. ID: {}", updatedTrainer.getId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while updating trainer", e);
        } finally {
            session.close();
        }
    }

    public void delete(Trainer trainer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.remove(trainer);
            transaction.commit();
            logger.info("Trainer deleted successfully. ID: {}", trainer.getId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while deleting trainer", e);
        } finally {
            session.close();
        }
    }

    public List<Trainer> getAllTrainers() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Trainer> trainerList = null;

        try {
            transaction = session.beginTransaction();
            Query<Trainer> query = session.createQuery("FROM Trainer", Trainer.class);
            trainerList = query.list();
            transaction.commit();
            logger.info("Retrieved all trainers. Count: {}", trainerList.size());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error("Error while finding all trainers", e);
        } finally {
            session.close();
        }

        return trainerList;
    }
}
