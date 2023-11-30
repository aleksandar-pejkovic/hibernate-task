package org.example.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    public void authenticateUser(String username, String password) {
        if (!authenticate(username, password)) {
            throw new RuntimeException("Authentication failed");
        } else {
            logger.info("Successful user authentication");
        }
    }

    private Boolean authenticate(String username, String password) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(*) FROM User WHERE username = :username AND password = :password", Long.class);
            query.setParameter("username", username);
            query.setParameter("password", password);

            return query.uniqueResult() > 0;
        } catch (Exception e) {
            logger.error("Authentication failed", e);
            return false;
        }
    }
}
