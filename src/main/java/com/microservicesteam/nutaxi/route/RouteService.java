package com.microservicesteam.nutaxi.route;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

    public Optional<Route> getRoute(String origin, String destination, String language) {
        Optional<DirectionsResult> directions = googleMapsRouteService.getDirections(origin, destination, language);

        if (directions.isPresent()) {
            List<String> overviewPolylines = stream(directions.get().routes)
                    .map(googleMapsRoute -> googleMapsRoute.overviewPolyline.getEncodedPath()).collect(toList());

            return Optional.of(Route.builder()
                    .overviewPolylines(overviewPolylines)
                    .build());
        } else {
            return Optional.empty();
        }
    }

}
