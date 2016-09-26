package com.microservicesteam.nutaxi.route;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.EncodedPolyline;
import com.microservicesteam.nutaxi.route.googlemaps.GoogleMapsRouteService;

@RunWith(MockitoJUnitRunner.class)
public class RouteServiceTest {

    private static final String POLYLINE = random(10);

    private RouteService underTest;

    @Mock
    private GoogleMapsRouteService googleMapsRouteService;

    @Before
    public void init() {
        underTest = new RouteService(googleMapsRouteService);
    }

    @Test
    public void shouldReturnWithRoute() {
        when(googleMapsRouteService.getDirections("Budapest", "Szeged", "hu"))
                .thenReturn(Optional.of(getDirections()));

        Optional<Route> route = underTest.getRoute("Budapest", "Szeged", "hu");

        assertThat(route.isPresent()).isTrue();
        assertThat(route.get().getOverviewPolylines()).hasSize(1);
        assertThat(route.get().getOverviewPolylines().get(0)).isEqualTo(POLYLINE);
    }

    @Test
    public void shouldNotReturnWithRouteWhenNoGoogleDirectionReturned() {
        when(googleMapsRouteService.getDirections("Budapest", "Szeged", "hu"))
                .thenReturn(Optional.empty());

        Optional<Route> route = underTest.getRoute("Budapest", "Szeged", "hu");

        assertThat(route.isPresent()).isFalse();
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
