package com.microservicesteam.nutaxi.route;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.model.DirectionsResult;
import com.microservicesteam.nutaxi.route.googlemaps.GoogleMapsRouteDetails;
import com.microservicesteam.nutaxi.route.googlemaps.GoogleMapsRouteService;

@Service
public class RouteService {

    private final GoogleMapsRouteService googleMapsRouteService;

    @Autowired
    public RouteService(GoogleMapsRouteService googleMapsRouteService) {
        this.googleMapsRouteService = googleMapsRouteService;
    }

    public Route getRoute(String origin, String destination, String language) {
        GoogleMapsRouteDetails route = googleMapsRouteService.getRoute(origin, destination, language);
        DirectionsResult response = route.getResponse();
        List<String> overviewPolylines = stream(response.routes)
                .map(googleMapsRoute -> googleMapsRoute.overviewPolyline.getEncodedPath()).collect(toList());

        return Route.builder()
                .overviewPolylines(overviewPolylines)
                .build();
    }

}
