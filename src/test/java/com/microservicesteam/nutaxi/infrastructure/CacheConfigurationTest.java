package com.microservicesteam.nutaxi.infrastructure;

import static com.microservicesteam.nutaxi.infrastructure.NutaxiRouteServiceApplicationTestConfiguration.GOOGLE_MAPS_ROUTE_SERVICE;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.context.annotation.FilterType.REGEX;

import java.util.Optional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.EncodedPolyline;
import com.microservicesteam.nutaxi.route.RouteRequest;
import com.microservicesteam.nutaxi.route.RouteService;
import com.microservicesteam.nutaxi.route.googlemaps.GoogleMapsRouteService;

import redis.embedded.RedisServer;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { NutaxiRouteServiceApplicationTestConfiguration.class, CacheConfiguration.class })
public class CacheConfigurationTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RouteService routeService;

    private static RedisServer redisServer;

    @BeforeClass
    public static void init() throws Exception {
        redisServer = new RedisServer(6378);
        redisServer.start();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        redisServer.stop();
    }

    @Test
    public void shouldFireUpCaching() {
        assertThat(redisTemplate).isNotNull();
    }

    @Test
    public void shouldUseCachingWhenRouteIsObtainedSecondTime() {
        assertThat(routeService).isNotNull();

        callRouteService();
        googleMapsServiceCalled();

        prepareForTheNextCall();

        callRouteService();
        googleMapsServiceNotCalled();

    }

    private static RouteRequest getRouteRequest() {
        return RouteRequest.builder()
                .origin("Budapest")
                .destination("Szeged")
                .language("hu")
                .build();
    }

    private void callRouteService() {
        routeService.getRoute(getRouteRequest());
    }

    private static void prepareForTheNextCall() {
        reset(GOOGLE_MAPS_ROUTE_SERVICE);
    }

    private static void googleMapsServiceCalled() {
        verify(GOOGLE_MAPS_ROUTE_SERVICE).getDirections(getRouteRequest());
    }

    private static void googleMapsServiceNotCalled() {
        verify(GOOGLE_MAPS_ROUTE_SERVICE, never()).getDirections(getRouteRequest());
    }

}

@SpringBootConfiguration
@EnableAutoConfiguration(exclude = { CloudConfiguration.class })
@ComponentScan(
        basePackages = {"com.microservicesteam.nutaxi.route" }, 
        excludeFilters = @ComponentScan.Filter(type = REGEX, pattern = "com\\.microservicesteam\\.nutaxi\\.route\\.googlemaps\\..*")
)
class NutaxiRouteServiceApplicationTestConfiguration {

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