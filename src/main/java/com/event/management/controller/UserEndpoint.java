package com.event.management.controller;

import com.event.management.domain.User;
import com.event.management.repository.UserRepository;
import com.event.management.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gatomulesei on 4/11/2017.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/users")
public class UserEndpoint {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserEndpoint.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void postInit(){
        if(userRepository.findByUsername("admin") == null){
            LOGGER.debug("Adding default admin.");

            final User defaultAdmin = new User();
            defaultAdmin.setFirstName("admin");
            defaultAdmin.setLastName("admin");
            defaultAdmin.setEmail("admin@admin.admin");
            defaultAdmin.setUsername("admin");
            defaultAdmin.setPassword("admin");

            List<String> roles = new ArrayList<>();
            roles.add("ADMIN");
            defaultAdmin.setRoles(roles);

            userService.addUser(defaultAdmin);
            LOGGER.debug("Default admin added.");
        } else {
            LOGGER.debug("Default admin already exists. Skipping creation.");
        }
    }

    /**
     * @return - list of all the users of the application
     */
    @CrossOrigin(origins = "*")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }


    /**
     * @param userId
     * @return - user by his ID
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
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
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
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
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
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
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody User user){
        if (!userRepository.exists(userId)) {
            return ResponseEntity.badRequest().body(new ObjectError("user.id", "Invalid user ID."));
        }

        User updatedUser = userRepository.findOne(userId);
        updatedUser.setUsername(user.getUsername());
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setRoles(user.getRoles());

        userRepository.save(updatedUser);
        URI location = URI.create("/users/" + updatedUser.getId());
        return ResponseEntity.created(location).build();
    }
}

