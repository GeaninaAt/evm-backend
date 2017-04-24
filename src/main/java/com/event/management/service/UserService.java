package com.event.management.service;

import com.event.management.domain.User;
import com.event.management.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by gatomulesei on 4/24/2017.
 */
@Service
@Transactional
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public User addUser(User user){
        LOGGER.info(String.format("Creating user '%s'", user.getUsername()));
        return userRepository.save(user);
    }
}
