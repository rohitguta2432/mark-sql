package com.api.controllers;


import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * @author RITESH SINGH
 * @since JDK 1.8
 * @version 1.0
 *
 */

@Component
public class ActiveGenreCollector implements HealthIndicator {

    @Override
    public Health health() {
        
        String status = "None";
        
        return new Health.Builder().up().withDetail("Active Genres", status).build();
    }
}