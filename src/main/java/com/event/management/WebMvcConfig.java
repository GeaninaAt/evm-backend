package com.event.management;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by gatomulesei on 4/5/2017.
 */
@Profile("REST")
@EnableWebMvc
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry){
        corsRegistry.addMapping("/rest/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("DELETE", "POST", "PUT", "GET", "OPTIONS")
                .allowCredentials(true).maxAge(3600);
    }
}
