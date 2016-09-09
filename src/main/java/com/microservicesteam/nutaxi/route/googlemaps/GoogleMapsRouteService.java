package com.microservicesteam.nutaxi.route.googlemaps;

import static com.google.maps.model.TravelMode.DRIVING;
import static com.google.maps.model.Unit.METRIC;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.GeoApiContext;
import com.microservicesteam.nutaxi.route.googlemaps.GoogleMapsRouteDetails.GoogleMapsRouteDetailsBuilder;

@Service
public class GoogleMapsRouteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleMapsRouteService.class);

    private GeoApiContext context;

    @Autowired
    public GoogleMapsRouteService(GeoApiContext context) {
        this.context = context;
    }

    public GoogleMapsRouteDetails getRoute(String origin, String destination, String language) {
        GoogleMapsRouteDetailsBuilder routeDetailsBuilder = GoogleMapsRouteDetails.builder();
        GoogleMapsDirectionsRequest directionsRequest = GoogleMapsDirectionsRequest.builder()
                .origin(origin)
                .destination(destination)
                .mode(DRIVING)
                .units(METRIC)
                .language(language)
                .build();
        routeDetailsBuilder.request(directionsRequest);

        doRequestAndWait(routeDetailsBuilder, directionsRequest);

        return routeDetailsBuilder.build();
    }

    private void doRequestAndWait(GoogleMapsRouteDetailsBuilder routeDetailsBuilder,
            GoogleMapsDirectionsRequest directionsRequest) {
        try {
            routeDetailsBuilder.response(directionsRequest.toDirectionsApiRequest(context).await());
        } catch (Exception e) {
            LOGGER.warn("Error during retrieving maps results", e);
            routeDetailsBuilder.error(e.getMessage());
        }
    }
}