package com.example.exstream.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Allows all endpoints
                .allowedOrigins("http://localhost:8080")  // Your Vue.js frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allowable HTTP methods
                .allowedHeaders("*")  // Allow any headers
                .allowCredentials(true)  // Allow cookies
                .maxAge(3600);  // Caching the preflight response for 1 hour
    }
}
