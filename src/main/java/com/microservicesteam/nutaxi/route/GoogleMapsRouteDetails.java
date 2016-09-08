package com.microservicesteam.nutaxi.route;

import com.google.maps.model.DirectionsResult;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@SuppressWarnings("PMD")
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GoogleMapsRouteDetails {

    @Getter
    private GoogleMapsDirectionsRequest request;

    @Getter
    private DirectionsResult response;

    @Getter
    private String error;
}
