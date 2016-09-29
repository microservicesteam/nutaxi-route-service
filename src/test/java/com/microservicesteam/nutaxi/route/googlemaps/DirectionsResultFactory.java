package com.microservicesteam.nutaxi.route.googlemaps;

import static org.apache.commons.lang3.RandomStringUtils.random;

import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.EncodedPolyline;

public final class DirectionsResultFactory {

    private static final String POLYLINE = random(10);

    private DirectionsResultFactory() {
    }

    public static DirectionsResult directions() {
        DirectionsResult result = new DirectionsResult();
        result.routes = new DirectionsRoute[] { directionsRoute() };
        return result;
    }

    private static DirectionsRoute directionsRoute() {
        DirectionsRoute directionsRoute = new DirectionsRoute();
        directionsRoute.overviewPolyline = new EncodedPolyline(POLYLINE);
        return directionsRoute;
    }

    public static String polyline() {
        return POLYLINE;
    }

}
