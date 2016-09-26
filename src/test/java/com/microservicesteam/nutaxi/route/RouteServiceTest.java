package com.microservicesteam.nutaxi.route;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.EncodedPolyline;
import com.microservicesteam.nutaxi.route.googlemaps.GoogleMapsRouteDetails;
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
    public void test() {
        DirectionsResult result = getResult();

        when(googleMapsRouteService.getRoute("Budapest", "Szeged", "hu"))
                .thenReturn(GoogleMapsRouteDetails.builder().response(result).build());

        Route route = underTest.getRoute("Budapest", "Szeged", "hu");

        assertThat(route.getOverviewPolylines()).hasSize(1);
        assertThat(route.getOverviewPolylines().get(0)).isEqualTo(POLYLINE);

    }

    private DirectionsResult getResult() {
        DirectionsResult result = new DirectionsResult();
        result.routes = new DirectionsRoute[] { getRoute() };
        return result;
    }

    private DirectionsRoute getRoute() {
        DirectionsRoute directionsRoute = new DirectionsRoute();
        directionsRoute.overviewPolyline = new EncodedPolyline(POLYLINE);
        return directionsRoute;
    }

}
