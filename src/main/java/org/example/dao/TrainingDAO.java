package org.example.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Training;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class TrainingDAO {

    private static final Logger logger = LogManager.getLogger(TrainingDAO.class);

    private final SessionFactory sessionFactory;

    @Autowired
    public TrainingDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Training training) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(training);
        logger.info("Training saved successfully. ID: {}", training.getId());
    }

    public Training findById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Training training = session.get(Training.class, id);
        if (training == null) {
            logger.error("Training not found by ID: {}", id);
        }
        return training;
    }

    public List<Training> getTraineeTrainingList(String username) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Training> criteria = builder.createQuery(Training.class);
        Root<Training> root = criteria.from(Training.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("trainee.user.username"), username));
        criteria.select(root).where(predicates.toArray(new Predicate[]{}));
        return session.createQuery(criteria).getResultList();
    }

    public List<Training> getTrainerTrainingList(String username) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Training> criteria = builder.createQuery(Training.class);
        Root<Training> root = criteria.from(Training.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("trainer.user.username"), username));
        criteria.select(root).where(predicates.toArray(new Predicate[]{}));
        return session.createQuery(criteria).getResultList();
    }

    public Training update(Training training) {
        Session session = sessionFactory.getCurrentSession();
        Training updatedTraining = (Training) session.merge(training);
        logger.info("Training updated successfully. ID: {}", updatedTraining.getId());
        return updatedTraining;
    }

    public void delete(Training training) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(training);
        logger.info("Training deleted successfully. ID: {}", training.getId());
    }

    public List<Training> getAllTrainings() {
        Session session = sessionFactory.getCurrentSession();
        Query<Training> query = session.createQuery("FROM Training", Training.class);
        List<Training> trainingList = query.getResultList();
        logger.info("Retrieved all trainings. Count: {}", trainingList.size());
        return trainingList;
    }
}
