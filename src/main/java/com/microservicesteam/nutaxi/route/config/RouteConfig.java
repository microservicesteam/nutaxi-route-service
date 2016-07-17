package com.microservicesteam.nutaxi.route.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.maps.GeoApiContext;

@Configuration
public class RouteConfig {

    @Value("${google-maps.api-key}")
    private String apiKey;

    
    @Bean
    public GeoApiContext geoApiContext() {
        return new GeoApiContext().setApiKey(apiKey);
    }
    
}
