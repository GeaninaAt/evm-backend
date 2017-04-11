package com.event.management.controller;

import com.event.management.domain.User;
import com.event.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by gatomulesei on 4/11/2017.
 */
@RestController
@RequestMapping(value = "/api")
public class UserEndpoint {

    @Autowired
    private UserRepository userRepository;


    /**
     * @return - list of all the retrieveAllUsers of the application
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }


    /**
     * @param userId
     * @return - user by his ID
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    public ResponseEntity<User> retrieveUser(@PathVariable Long userId) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    /**
     * Delete a user by his ID
     * @param userId
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable Long userId) {
        User user = userRepository.findOne(userId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUsername = auth.getName();

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else if (user.getUsername().equalsIgnoreCase(loggedUsername)) {
            throw new RuntimeException("You cannot delete your account!");
        } else {
            userRepository.delete(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

    }

    /**
     * Add a user
     * @param user
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (userRepository.findOneByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists!");
        }
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    /**
     * Edit details of a user
     * @param user
     * @return - modified user
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public User updateUser(@RequestBody User user) {
        if (userRepository.findOneByUsername(user.getUsername()) != null
                && userRepository.findOneByUsername(user.getUsername()).getId() != user.getId()) {
            throw new RuntimeException("Username already exists!");
        }
        return userRepository.save(user);
    }

}

