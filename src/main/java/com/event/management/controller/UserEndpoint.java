package com.event.management.controller;

import com.event.management.domain.User;
import com.event.management.repository.UserRepository;
import com.event.management.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;

/**
 * Created by gatomulesei on 4/5/2017.
 */
//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/rest/users")
public class UserEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserEndpoint.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void postInit(){
        if(userRepository.findByUsername("user") == null){
            LOGGER.debug("Adding default user: 'user'");

            final User defaultUser = new User();
            defaultUser.setUsername("user");
            defaultUser.setPassword("pass");
            defaultUser.setEmail("user@mail");
            defaultUser.setFirstName("default");
            defaultUser.setLastName("default");

            userService.addUser(defaultUser);
            LOGGER.debug("Default user added.");
        } else {
            LOGGER.debug("Default user already exists. Skipping creation...");
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody @Valid User user){
        if(userRepository.findByEmail(user.getEmail()) != null){
            LOGGER.info(String.format("Registration cancelled: user with e-mail '%s' already exists."), user.getEmail());
            return ResponseEntity.badRequest().body(new ObjectError("user.email",
                    String.format("User with e-mail '%s' already exists.", user.getEmail())));
        }
        if(userRepository.findByUsername(user.getUsername()) != null){
            LOGGER.info(String.format("Registration cancelled: user with username '%s' already exists."), user.getUsername());
            return ResponseEntity.badRequest().body(new ObjectError("user.username",
                    String.format("User with username '%s' already exists.", user.getUsername())));
        }
        if(!user.getPassword().equals(user.getMatchPassword())){
            LOGGER.info("Registration cancelled: passwords do not match.");
            return ResponseEntity.badRequest().body(new ObjectError("user.password", "Passwords do not match."));
        }

        //TODO: encode user's password - maybe base64?

        final User createdUser = userService.addUser(user);
        final URI location = URI.create("/rest/users/" + createdUser.getId());

        return ResponseEntity.created(location).body(createdUser.getId());
    }

    @RequestMapping(method = RequestMethod.GET)
    public Principal user(Principal principal){
        return principal;
    }
}
