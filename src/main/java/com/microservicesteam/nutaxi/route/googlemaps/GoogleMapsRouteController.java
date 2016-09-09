package com.microservicesteam.nutaxi.route.googlemaps;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/route")
public class GoogleMapsRouteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleMapsRouteController.class);

    @Autowired
    private GoogleMapsRouteService routeService;

    @GetMapping
    public GoogleMapsRouteDetails route(@RequestParam String origin, @RequestParam String destination, Locale locale) {
        LOGGER.debug("Querying route from '{}' to '{}' with locale {}", origin, destination, locale.getLanguage());
        return routeService.getRoute(origin, destination, locale.getLanguage());
    }

}