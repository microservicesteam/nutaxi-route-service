package com.microservicesteam.nutaxi.infrastructure;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.context.annotation.FilterType.REGEX;

import java.util.Optional;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.EncodedPolyline;
import com.microservicesteam.nutaxi.route.googlemaps.GoogleMapsRouteService;

@SpringBootConfiguration
@EnableAutoConfiguration(exclude = { CloudConfiguration.class })
@ComponentScan(basePackages = {
        "com.microservicesteam.nutaxi.route" }, excludeFilters = @ComponentScan.Filter(type = REGEX, pattern = "com\\.microservicesteam\\.nutaxi\\.route\\.googlemaps\\..*"))
public class NutaxiRouteServiceApplicationTestConfiguration {

    private static final String POLYLINE = random(10);

    public static final GoogleMapsRouteService GOOGLE_MAPS_ROUTE_SERVICE = mock(GoogleMapsRouteService.class);

    @Bean
    public GoogleMapsRouteService mockedGoogleMapsRouteService() {
        when(GOOGLE_MAPS_ROUTE_SERVICE.getDirections(anyObject())).thenReturn(Optional.of(getDirections()));

        return GOOGLE_MAPS_ROUTE_SERVICE;
    }

    private static DirectionsResult getDirections() {
        DirectionsResult result = new DirectionsResult();
        result.routes = new DirectionsRoute[] { getRoute() };
        return result;
    }

    private static DirectionsRoute getRoute() {
        DirectionsRoute directionsRoute = new DirectionsRoute();
        directionsRoute.overviewPolyline = new EncodedPolyline(POLYLINE);
        return directionsRoute;
    }

}
