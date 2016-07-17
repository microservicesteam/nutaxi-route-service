package com.microservicesteam.nutaxi.route.model;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;

import lombok.Builder;
import lombok.Getter;

@Builder
public class GoogleMapsDirectionsRequest {

    @Getter
    private String origin;

    @Getter
    private String destination;

    @Getter
    private TravelMode mode;

    @Getter
    private Unit units;

    @Getter
    private String language;

    public DirectionsApiRequest toDirectionsApiRequest(GeoApiContext context) {
        return DirectionsApi.newRequest(context)
                .origin(origin)
                .destination(destination)
                .mode(mode)
                .units(units)
                .language(language);
    }

}
