package com.microservicesteam.nutaxi.route;

import static com.google.common.collect.Lists.newArrayList;
import static com.microservicesteam.nutaxi.route.googlemaps.DirectionsResultFactory.polyline;

public final class RouteFactory {

    private RouteFactory() {
    }

    public static Route route() {
        return Route.builder()
                .overviewPolylines(newArrayList(polyline()))
                .build();
    }
    
    public static Route route(String polyline) {
        return Route.builder()
                .overviewPolylines(newArrayList(polyline))
                .build();
    }

}
