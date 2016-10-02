package com.microservicesteam.nutaxi.infrastructure;

import static com.microservicesteam.nutaxi.infrastructure.NutaxiRouteServiceApplicationTestConfiguration.GOOGLE_MAPS_ROUTE_SERVICE;
import static com.microservicesteam.nutaxi.route.RouteRequestFactory.routeRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.microservicesteam.nutaxi.route.RouteService;

import redis.embedded.RedisServer;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { NutaxiRouteServiceApplicationTestConfiguration.class, CacheConfiguration.class })
public class CacheConfigurationIntegrationTest {

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

    private void callRouteService() {
        routeService.getRoute(routeRequest());
    }

    private static void prepareForTheNextCall() {
        reset(GOOGLE_MAPS_ROUTE_SERVICE);
    }

    private static void googleMapsServiceCalled() {
        verify(GOOGLE_MAPS_ROUTE_SERVICE).getDirections(routeRequest());
    }

    private static void googleMapsServiceNotCalled() {
        verify(GOOGLE_MAPS_ROUTE_SERVICE, never()).getDirections(routeRequest());
    }

}