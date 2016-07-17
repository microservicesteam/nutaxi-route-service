package com.microservicesteam.nutaxi.route.model;

import com.google.maps.model.DirectionsResult;

import lombok.Builder;
import lombok.Getter;

@Builder
public class GoogleMapsRouteDetails implements RouteDetails<GoogleMapsDirectionsRequest, DirectionsResult, String> {

    @Getter
    private GoogleMapsDirectionsRequest request;

    @Getter
    private DirectionsResult response;

    @Getter
    private String error;
}
