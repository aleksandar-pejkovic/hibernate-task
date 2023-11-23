package org.example.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.User;
import org.example.storage.FileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDAO {

    private static final Logger logger = LogManager.getLogger(UserDAO.class);

    private final FileStorage fileStorage;

    private final Map<Long, User> userMap = new HashMap<>();

    private static long idCounter = 1;

    @Autowired
    public UserDAO(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    public void save(User user) {
        long id = idCounter++;
        user.setId(id);
        userMap.put(id, user);
    }

    public User findById(long id) {
        User user = userMap.get(id);
        if (user == null) {
            logger.error("User not found by ID: {}", id);
        }
        return user;
    }

    public void update(User user) {
        if (!userMap.containsKey(user.getId())) {
            logger.error("User not found for update: {}", user);
        } else {
            userMap.put(user.getId(), user);
        }
    }

    public void delete(long id) {
        if (!userMap.containsKey(id)) {
            logger.error("User not found for deletion with ID: {}", id);
        } else {
            userMap.remove(id);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = (List<User>) fileStorage.getEntityData().get("users");
        if (users == null || users.isEmpty()) {
            logger.error("No users found.");
        }
        return users;
    }
}
