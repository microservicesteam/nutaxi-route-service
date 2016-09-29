package com.microservicesteam.nutaxi.route;

import static com.microservicesteam.nutaxi.route.RouteRequestFactory.routeRequest;
import static com.microservicesteam.nutaxi.route.googlemaps.DirectionsResultFactory.directions;
import static com.microservicesteam.nutaxi.route.googlemaps.DirectionsResultFactory.polyline;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.microservicesteam.nutaxi.route.googlemaps.GoogleMapsRouteService;

@RunWith(MockitoJUnitRunner.class)
public class RouteServiceTest {

    private RouteService underTest;

    @Mock
    private GoogleMapsRouteService googleMapsRouteService;

    @Before
    public void init() {
        underTest = new RouteService(googleMapsRouteService);
    }

    @Test
    public void shouldReturnWithRoute() {
        when(googleMapsRouteService.getDirections(routeRequest()))
                .thenReturn(Optional.of(directions()));

        Optional<Route> route = underTest.getRoute(routeRequest());

        assertThat(route.isPresent()).isTrue();
        assertThat(route.get().getOverviewPolylines()).hasSize(1);
        assertThat(route.get().getOverviewPolylines().get(0)).isEqualTo(polyline());
    }

    @Test
    public void shouldNotReturnWithRouteWhenNoGoogleDirectionReturned() {
        when(googleMapsRouteService.getDirections(routeRequest()))
                .thenReturn(Optional.empty());

        Optional<Route> route = underTest.getRoute(routeRequest());

        assertThat(route.isPresent()).isFalse();
    }

}
