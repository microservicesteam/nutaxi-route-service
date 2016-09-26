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
        String origin = "Szeged";
        String destination = "Budapest";
        String language = "hu";

        Optional<DirectionsResult> result = underTest.getDirections(origin, destination, language);

        assertThat(result).isNotNull();
    }

}