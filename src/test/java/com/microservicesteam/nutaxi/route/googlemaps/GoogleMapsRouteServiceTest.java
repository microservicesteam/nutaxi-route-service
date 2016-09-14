package com.microservicesteam.nutaxi.route.googlemaps;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.maps.GeoApiContext;

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
    public void shouldReturnWithRoute() {
        String origin = "Szeged";
        String destination = "Budapest";
        String language = "hu";

        GoogleMapsRouteDetails result = underTest.getRoute(origin, destination, language);

        assertThat(result).isNotNull();
    }

}