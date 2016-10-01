package com.microservicesteam.nutaxi.route;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.microservicesteam.nutaxi.route.googlemaps.GoogleMapsRouteService;

@Service
public class RouteService {

    private final GoogleMapsRouteService googleMapsRouteService;

    @Autowired
    public RouteService(GoogleMapsRouteService googleMapsRouteService) {
        this.googleMapsRouteService = googleMapsRouteService;
    }

    @Cacheable("routes")
    public Optional<Route> getRoute(RouteRequest request) {
        return googleMapsRouteService.getDirections(request).map(directions -> {
            return Route.builder()
                    .overviewPolylines(stream(directions.routes)
                            .map(googleMapsRoute -> googleMapsRoute.overviewPolyline.getEncodedPath())
                            .collect(toList()))
                    .build();
        });
    }

}
