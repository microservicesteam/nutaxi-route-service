package com.microservicesteam.nutaxi.route.googlemaps;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.microservicesteam.nutaxi.route.RouteRequest;

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
        Optional<DirectionsResult> result = underTest.getDirections(RouteRequest.builder()
                .origin("Budapest")
                .destination("Szeged")
                .language("hu")
                .build());

        assertThat(result).isNotNull();
    }

}