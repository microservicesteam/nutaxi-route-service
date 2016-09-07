package com.microservicesteam.nutaxi.route.service;

import static com.google.maps.model.TravelMode.DRIVING;
import static com.google.maps.model.Unit.METRIC;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.GeoApiContext;
import com.microservicesteam.nutaxi.route.model.GoogleMapsDirectionsRequest;
import com.microservicesteam.nutaxi.route.model.GoogleMapsRouteDetails;
import com.microservicesteam.nutaxi.route.model.GoogleMapsRouteDetails.GoogleMapsRouteDetailsBuilder;

@Service
public class GoogleMapsRouteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleMapsRouteService.class);

    @Autowired
    private GeoApiContext context;

    public GoogleMapsRouteDetails getRoute(String origin, String destination, String language) {
        GoogleMapsRouteDetailsBuilder routeDetailsBuilder = GoogleMapsRouteDetails.builder();
        GoogleMapsDirectionsRequest directionsRequest = createRequest(origin, destination, language);
        routeDetailsBuilder.request(directionsRequest);

        doRequestAndWait(routeDetailsBuilder, directionsRequest);

        return routeDetailsBuilder.build();
    }

    private static GoogleMapsDirectionsRequest createRequest(String origin, String destination, String language) {
        return GoogleMapsDirectionsRequest.builder()
                .origin(origin)
                .destination(destination)
                .mode(DRIVING)
                .units(METRIC)
                .language(language)
                .build();
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
