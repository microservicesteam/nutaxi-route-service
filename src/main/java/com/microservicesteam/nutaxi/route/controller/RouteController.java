package com.microservicesteam.nutaxi.route.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservicesteam.nutaxi.route.model.RouteDetails;
import com.microservicesteam.nutaxi.route.service.RouteService;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteController.class);

    @Autowired
    private RouteService routeService;

    @GetMapping
    public RouteDetails<?, ?, ?> route(@RequestParam String origin, @RequestParam String destination, Locale locale) {
        LOGGER.debug("Querying route from '{}' to '{}' with locale {}", origin, destination, locale.getLanguage());
        return routeService.getRoute(origin, destination, locale.getLanguage());
    }

}
