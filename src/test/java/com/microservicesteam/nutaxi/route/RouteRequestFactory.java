package com.microservicesteam.nutaxi.route;

public final class RouteRequestFactory {

    private RouteRequestFactory() {
    }

    public static RouteRequest routeRequest() {
        return RouteRequest.builder()
                .origin("Budapest")
                .destination("Szeged")
                .language("hu")
                .build();
    }
}
