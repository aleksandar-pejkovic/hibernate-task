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

import java.util.Random;

@Component
public class CredentialsGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final int LENGTH = 10;

    private static final Random random = new Random();

    private static final Logger logger = LogManager.getLogger(CredentialsGenerator.class);

    private final SessionFactory sessionFactory;

    @Autowired
    public CredentialsGenerator(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public String generateRandomPassword() {
        logger.info("Generating password...");
        StringBuilder password = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(randomIndex));
        }
        return password.toString();
    }

    @Transactional
    public String generateUsername(User user) {
        logger.info("Generating username...");
        String baseUsername = user.getUsername() + "." + user.getLastName();
        long count = fetchCountForMatchingUsername(baseUsername);
        return (count > 0) ? baseUsername + ++count : baseUsername;
    }

    private long fetchCountForMatchingUsername(String baseUsername) {
        Session session = sessionFactory.getCurrentSession();
        Query<Long> query = session.createQuery("SELECT COUNT(*) FROM User WHERE username LIKE :baseUsername",
                Long.class);
        query.setParameter("baseUsername", baseUsername + "%");
        return query.uniqueResult();
    }
}
