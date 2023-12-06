package org.example.dao;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Training;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class TrainingDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public TrainingDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Training save(Training training) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(training);
        log.info("Training saved successfully. ID: {}", training.getId());
        return training;
    }

    public Training findById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Training training = session.get(Training.class, id);
        if (Optional.ofNullable(training).isEmpty()) {
            log.error("Training not found by ID: {}", id);
        }
        return training;
    }

    public List<Training> getTraineeTrainingList(String username, int trainingDuration) {
        return getTrainingList("trainee", username, trainingDuration);
    }

    public List<Training> getTrainerTrainingList(String username, int trainingDuration) {
        return getTrainingList("trainer", username, trainingDuration);
    }

    public Training update(Training training) {
        Session session = sessionFactory.getCurrentSession();
        Training updatedTraining = session.merge(training);
        log.info("Training updated successfully. ID: {}", updatedTraining.getId());
        return updatedTraining;
    }

    public boolean delete(Training training) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.merge(training);
            session.remove(training);
            log.info("Training deleted successfully. ID: {}", training.getId());
            return true;
        } catch (EntityNotFoundException e) {
            log.error("there was an error. Training was not deleted. ID: {}", training.getId(), e);
            return false;
        }
    }

    public List<Training> getAllTrainings() {
        Session session = sessionFactory.getCurrentSession();
        Query<Training> query = session.createQuery("FROM Training", Training.class);
        List<Training> trainingList = query.getResultList();
        log.info("Retrieved all trainings. Count: {}", trainingList.size());
        return trainingList;
    }

    private List<Training> getTrainingList(String entityType, String username, int trainingDuration) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Training> criteria = builder.createQuery(Training.class);
        Root<Training> root = criteria.from(Training.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get(entityType).get("user").get("username"), username));
        predicates.add(builder.greaterThan(root.get("trainingDuration"), trainingDuration));
        criteria.select(root).where(predicates.toArray(new Predicate[]{}));
        return session.createQuery(criteria).getResultList();
    }
}
