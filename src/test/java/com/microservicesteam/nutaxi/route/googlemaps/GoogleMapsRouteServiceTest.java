package com.microservicesteam.nutaxi.route.googlemaps;

import static com.microservicesteam.nutaxi.route.RouteRequestFactory.routeRequest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;

@RunWith(MockitoJUnitRunner.class)
public class GoogleMapsRouteServiceTest {

    private GoogleMapsRouteService underTest;

    @Mock
    private GeoApiContext context;

    @Before
    public void init() {
        underTest = new GoogleMapsRouteService(context);
    }

    @Test
    public void shouldReturnWithDirections() {
        Optional<DirectionsResult> result = underTest.getDirections(routeRequest());

        assertThat(result).isNotNull();
    }

}