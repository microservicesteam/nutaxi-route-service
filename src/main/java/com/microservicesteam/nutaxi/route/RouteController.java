package com.microservicesteam.nutaxi.route;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/route")
public class RouteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteController.class);

    @Autowired
    private RouteService routeService;

    @GetMapping
    public ResponseEntity<Route> route(@RequestParam String origin, @RequestParam String destination, Locale locale) {
        LOGGER.debug("Querying route from '{}' to '{}' with locale {}", origin, destination, locale.getLanguage());

        Optional<Route> route = routeService.getRoute(RouteRequest.builder()
                .origin(origin)
                .destination(destination)
                .language(locale.getLanguage())
                .build());

        if (route.isPresent()) {
            return new ResponseEntity<>(route.get(), OK);
        } else {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

}
