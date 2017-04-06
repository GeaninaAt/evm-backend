package com.event.management.service;

import com.event.management.domain.User;
import com.event.management.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gatomulesei on 4/5/2017.
 */
@Service("userService")
@Transactional
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public User addUser(User user) {
        LOGGER.info(String.format("Creating user '%s'", user.getUsername()));
        return userRepository.save(user);
    }

    public User getUser(Long userId) {
        LOGGER.info(String.format("Retrieving user with id '%s'", userId));
        return userRepository.findOne(userId);
    }

    public List<User> getAllUsers() {
        LOGGER.info(String.format("Retrieving all users..."));
        return userRepository.findAll();
    }

    public void deleteUser(Long userId) {
        LOGGER.info(String.format("Deleting user with id '%s'", userId));
        userRepository.delete(userId);
    }
}