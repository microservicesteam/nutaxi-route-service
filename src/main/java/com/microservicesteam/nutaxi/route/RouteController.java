package com.microservicesteam.nutaxi.route;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

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
    public ResponseEntity<Route> route(@RequestParam String origin, @RequestParam String destination) {
        LOGGER.debug("Querying route from '{}' to '{}'", origin, destination);

        return routeService.getRoute(RouteRequest.builder()
                .origin(origin)
                .destination(destination)
                .build())
                .map(route -> new ResponseEntity<>(route, OK))
                .orElse(new ResponseEntity<>(NOT_FOUND));
    }

}
