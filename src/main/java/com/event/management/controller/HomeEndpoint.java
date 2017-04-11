package com.event.management.controller;

import com.event.management.domain.User;
import com.event.management.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

/**
 * Created by gatomulesei on 4/11/2017.
 */
@RestController
public class HomeEndpoint {

    @Autowired
    private UserRepository userRepository;

    /**
     * Method used for user registration - does not require any authentication.
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (userRepository.findOneByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists!");
        }
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        user.setRoles(roles);
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.CREATED);
    }

    /**
     * Method which returns the logged user.
     *
     * @param principal
     * @return Principal - java security principal object
     */
    @RequestMapping("/user")
    public User user(Principal principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUsername = auth.getName();
        return userRepository.findOneByUsername(loggedUsername);
    }

    /**
     * @param username
     * @param password
     * @param response
     * @return JSON containing the token and the user after a successful authentication.
     * @throws IOException
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> login(@RequestParam String username, @RequestParam String password,
                                                     HttpServletResponse response) throws IOException {

        String token;
        User user = userRepository.findOneByUsername(username);
        Map<String, Object> tokenMap = new HashMap<>();

        if (user != null && user.getPassword().equals(password)) {
            token = Jwts.builder().setSubject(username).claim("roles", user.getRoles()).setIssuedAt(new Date())
                    .signWith(SignatureAlgorithm.HS256, "secretkey").compact();
            tokenMap.put("token", token);
            tokenMap.put("user", user);
            return new ResponseEntity<>(tokenMap, HttpStatus.OK);
        } else {
            tokenMap.put("token", null);
            return new ResponseEntity<>(tokenMap, HttpStatus.UNAUTHORIZED);
        }
    }
}

