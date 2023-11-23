package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.dao.UserDAO;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void createUser(User user) {
        // You can add any business logic or validation before saving the user
        userDAO.save(user);
        logger.info("User created successfully: " + user.getId());
    }

    public User getUserById(long id) {
        // You can add any business logic or validation before retrieving the user
        User user = userDAO.findById(id);
        if (user != null) {
            logger.info("User retrieved successfully: " + user.getId());
        } else {
            logger.info("User not found with ID: " + id);
        }
        return user;
    }

    public void updateUser(User user) {
        // You can add any business logic or validation before updating the user
        userDAO.update(user);
        logger.info("User updated successfully: " + user.getId());
    }

    public void deleteUser(User user) {
        // You can add any business logic or validation before deleting the user
        userDAO.delete(user);
        logger.info("User deleted successfully: " + user.getId());
    }

    public List<User> getAllUsers() {
        // You can add any business logic or validation before retrieving all users
        List<User> userList = userDAO.findAllUsers();
        logger.info("Retrieved all users. Count: " + userList.size());
        return userList;
    }
}
