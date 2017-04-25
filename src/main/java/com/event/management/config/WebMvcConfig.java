package com.event.management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by gatomulesei on 4/12/2017.
 */
@Profile("REST")
@EnableWebMvc
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
			"classpath:/META-INF/resources/", "classpath:/resources/",
			"classpath:/static/", "classpath:/public/" };
    /**
     * Set up the CORS headers so that I can use all of the REST endpoints
     * from the Front End app installed on another server
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/rest/**")
                //.allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("DELETE", "POST", "GET", "OPTIONS")
                .allowCredentials(true).maxAge(3600);
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	if (!registry.hasMappingForPattern("/webjars/**")) {
    		registry.addResourceHandler("/webjars/**").addResourceLocations(
    				"classpath:/META-INF/resources/webjars/");
    	}
    	if (!registry.hasMappingForPattern("/**")) {
    		registry.addResourceHandler("/**").addResourceLocations("/static/app/bower_components");
    	}
    }
    
}
