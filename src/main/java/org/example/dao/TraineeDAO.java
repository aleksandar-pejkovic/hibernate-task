package org.example.dao;

import lombok.extern.slf4j.Slf4j;
import org.example.model.Trainee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class TraineeDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public TraineeDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Trainee trainee) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(trainee);
    }

    public Trainee findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query<Trainee> query = session.createQuery("FROM Trainee t where t.user.username = :username", Trainee.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

    public Trainee update(Trainee trainee) {
        Session session = sessionFactory.getCurrentSession();
        return session.merge(trainee);
    }

    public void delete(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query<Long> subQuery = session.createQuery("SELECT t.id FROM Trainee t WHERE t.user.username = :username",
                Long.class);
        subQuery.setParameter("username", username);

        Long traineeId = subQuery.uniqueResult();

        if (traineeId != null) {
            Trainee trainee = session.get(Trainee.class, traineeId);
            session.remove(trainee);
            log.info("Trainee deleted successfully. USERNAME: {}", username);
        } else {
            log.error("Trainee not found for USERNAME: {}", username);
        }
    }

    public List<Trainee> getAllTrainees() {
        Session session = sessionFactory.getCurrentSession();
        Query<Trainee> query = session.createQuery("FROM Trainee", Trainee.class);
        return query.list();
    }
}
