package com.microservicesteam.nutaxi.route;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.google.maps.model.DirectionsResult;
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
        Optional<DirectionsResult> directions = googleMapsRouteService.getDirections(request);

        if (directions.isPresent()) {
            return mapDirectionsToRoute(directions.get());
        } else {
            return Optional.empty();
        }
    }

    private static Optional<Route> mapDirectionsToRoute(DirectionsResult directionsResult) {
        List<String> overviewPolylines = stream(directionsResult.routes)
                .map(googleMapsRoute -> googleMapsRoute.overviewPolyline.getEncodedPath()).collect(toList());

        return Optional.of(Route.builder()
                .overviewPolylines(overviewPolylines)
                .build());
    }

}
