package org.example.utils;

import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAuthentication {

    private static final Logger logger = LogManager.getLogger(UserAuthentication.class);

    private final SessionFactory sessionFactory;

    @Autowired
    public UserAuthentication(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void authenticateUser(User user) {
        if (!authenticate(user)) {
            throw new RuntimeException("Authentication failed");
        } else {
            logger.info("Successful user authentication");
        }
    }

    @Transactional
    private Boolean authenticate(User user) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(*) FROM User WHERE username = :username AND password = :password", Long.class);
            query.setParameter("username", user.getUsername());
            query.setParameter("password", user.getPassword());

            return query.uniqueResult() > 0;
        } catch (Exception e) {
            logger.error("Authentication failed", e);
            return false;
        }
    }
}
