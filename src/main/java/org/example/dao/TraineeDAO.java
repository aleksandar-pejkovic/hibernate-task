package org.example.dao;

import java.util.List;
import java.util.Optional;

import org.example.model.Trainee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class TraineeDAO extends AbstractDAO {

    @Autowired
    public TraineeDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Trainee saveTrainee(Trainee trainee) {
        return (Trainee) save(trainee);
    }

    public Trainee findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query<Trainee> query = session.createQuery("FROM Trainee t where t.user.username = :username", Trainee.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

    public Trainee updateTrainee(Trainee trainee) {
        return (Trainee) update(trainee);
    }

    public boolean deleteByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query<Long> subQuery = session.createQuery("DELETE FROM Trainee t WHERE t.user.username = :username",
                Long.class);
        subQuery.setParameter("username", username);

        Long traineeId = subQuery.uniqueResult();

        if (Optional.ofNullable(traineeId).isPresent()) {
            Trainee trainee = session.get(Trainee.class, traineeId);
            session.remove(trainee);
            log.info("Trainee deleted successfully. USERNAME: {}", username);
            return true;
        } else {
            log.error("Trainee not found for USERNAME: {}", username);
            return false;
        }
    }

    public List<Trainee> getAllTrainees() {
        return (List<Trainee>) findAll(Trainee.class);
    }
}
