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
        return (Trainee) session.merge(trainee);
    }

    public void delete(Trainee trainee) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(trainee);
    }

    public List<Trainee> getAllTrainees() {
        Session session = sessionFactory.getCurrentSession();
        Query<Trainee> query = session.createQuery("FROM Trainee", Trainee.class);
        return query.list();
    }
}
