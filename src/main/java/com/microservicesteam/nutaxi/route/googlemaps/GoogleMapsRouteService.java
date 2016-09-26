package com.microservicesteam.nutaxi.route.googlemaps;

import static com.google.maps.model.TravelMode.DRIVING;
import static com.google.maps.model.Unit.METRIC;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;

@Service
public class GoogleMapsRouteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleMapsRouteService.class);

    private GeoApiContext context;

    @Autowired
    public GoogleMapsRouteService(GeoApiContext context) {
        this.context = context;
    }

    public Optional<DirectionsResult> getDirections(String origin, String destination, String language) {
        GoogleMapsDirectionsRequest directionsRequest = GoogleMapsDirectionsRequest.builder()
                .origin(origin)
                .destination(destination)
                .mode(DRIVING)
                .units(METRIC)
                .language(language)
                .build();

        try {
            return Optional.of(directionsRequest.toDirectionsApiRequest(context).await());
        } catch (Exception e) {
            LOGGER.warn("Error during retrieving maps results", e);
            return Optional.empty();
        }
    }

}