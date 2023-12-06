package org.example.utils;

import lombok.extern.slf4j.Slf4j;
import org.example.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Component
@Slf4j
public class CredentialsGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final int LENGTH = 10;

    private static final Random random = new Random();

    private final SessionFactory sessionFactory;

    @Autowired
    public CredentialsGenerator(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public String generateRandomPassword() {
        log.info("Generating password...");
        StringBuilder password = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(randomIndex));
        }
        // for testing with h2 in-memory db
        return "1234567890";
//        return password.toString();
    }

    @Transactional(readOnly = true)
    public String generateUsername(User user) {
        log.info("Generating username...");
        String baseUsername = user.getFirstName() + "." + user.getLastName();
        long count = fetchCountForMatchingUsername(baseUsername);
        if (count > 0) {
            long usernameSuffix = count + 1;
            return baseUsername + usernameSuffix;
        }
        return baseUsername;
    }

    private long fetchCountForMatchingUsername(String baseUsername) {
        Session session = sessionFactory.getCurrentSession();
        Query<Long> query = session.createQuery("SELECT COUNT(*) FROM User WHERE username LIKE :baseUsername",
                Long.class);
        query.setParameter("baseUsername", baseUsername + "%");
        return query.uniqueResult();
    }
}
