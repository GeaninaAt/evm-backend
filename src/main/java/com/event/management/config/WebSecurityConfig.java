package com.event.management.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Class used to modify/override the default spring boot security configurations
 * Created by gatomulesei on 4/11/2017.
 */
@Configurable
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Override some configs of the WebSecurity - ignore some requests & request patterns
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/", "/index.html", "/app/**", "/register", "/authenticate", "/favicon.ico");
    }


    /**
     * Override HttpSecurity for the webapp = specify authorization criteria
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // starts authorizing configurations
                .antMatchers(HttpMethod.OPTIONS).permitAll()//allow cors
                .antMatchers(HttpMethod.OPTIONS,"**").permitAll()
                .antMatchers(HttpMethod.GET).permitAll()//allow cors
                .antMatchers(HttpMethod.GET,"**").permitAll()
                .antMatchers(HttpMethod.GET,"/users").permitAll()
                .antMatchers(HttpMethod.POST).permitAll()//allow cors
                .antMatchers(HttpMethod.POST,"**").permitAll()
                .antMatchers(HttpMethod.POST,"/users").permitAll()
                .antMatchers(HttpMethod.DELETE).permitAll()//allow cors
                .antMatchers(HttpMethod.DELETE,"**").permitAll()
                .antMatchers(HttpMethod.DELETE,"/users").permitAll()
                .anyRequest().fullyAuthenticated() // authenticate all remaining URLS
                .antMatchers(HttpMethod.OPTIONS,"/users").permitAll()
                .and()
                .addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class) // adding JWT filter
                .httpBasic() // enable basic authentication
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // configure the session as stateless -  no session in the server
                .and()
                .csrf().disable(); // disable CSRF - Cross Site Request Forgery
    }
    
}

