package com.event.management.service;

import com.event.management.domain.User;
import com.event.management.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Collection;

/**
 * Created by gatomulesei on 4/5/2017.
 */
@Component
public class ConfigUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetails() {

            private static final long serialVersionUID = 1L;

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                final User user = userRepository.findByUsername(username);
                if(user == null){
                    LOGGER.info(String.format("Could not find user: '%s'", username));
                    return null;
                }
                if(user.getPassword() == null){
                    return null;
                }
                return new String(user.getPassword().getBytes(), StandardCharsets.UTF_8);
            }

            @Override
            public String getUsername() {
                final User user = userRepository.findByUsername(username);
                if(user == null){
                    LOGGER.info(String.format("Could not find user: '%s'", username));
                    return null;
                }
                return user.getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
