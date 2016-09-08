package com.microservicesteam.nutaxi.route;

import static com.microservicesteam.nutaxi.route.GoogleMapsDirectionsRequest.drivingDirectionsRequest;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.maps.GeoApiContext;
import com.microservicesteam.nutaxi.route.GoogleMapsRouteDetails.GoogleMapsRouteDetailsBuilder;
import com.netflix.appinfo.AmazonInfo;

@EnableDiscoveryClient
@SpringBootApplication
public class NutaxiRouteServiceApplication {

    @Value("${google-maps.api-key}")
    private String apiKey;

    @Bean
    public GeoApiContext geoApiContext() {
        return new GeoApiContext().setApiKey(apiKey);
    }

    @Bean
    @Profile("docker-aws")
    public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) {
        EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
        AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
        config.setDataCenterInfo(info);
        return config;
    }

    public static void main(String[] args) {
        SpringApplication.run(NutaxiRouteServiceApplication.class, args);
    }

    @RestController
    @RequestMapping("/api/route")
    public static class RouteController {

        private static final Logger LOGGER = LoggerFactory.getLogger(RouteController.class);

        @Autowired
        private GoogleMapsRouteService routeService;

        @GetMapping
        public GoogleMapsRouteDetails route(@RequestParam String origin, @RequestParam String destination, Locale locale) {
            LOGGER.debug("Querying route from '{}' to '{}' with locale {}", origin, destination, locale.getLanguage());
            return routeService.getRoute(origin, destination, locale.getLanguage());
        }

    }

    @Service
    public static class GoogleMapsRouteService {

        private static final Logger LOGGER = LoggerFactory.getLogger(GoogleMapsRouteService.class);

        @Autowired
        private GeoApiContext context;

        public GoogleMapsRouteDetails getRoute(String origin, String destination, String language) {
            GoogleMapsRouteDetailsBuilder routeDetailsBuilder = GoogleMapsRouteDetails.builder();
            GoogleMapsDirectionsRequest directionsRequest = drivingDirectionsRequest(origin, destination, language);
            routeDetailsBuilder.request(directionsRequest);

            doRequestAndWait(routeDetailsBuilder, directionsRequest);

            return routeDetailsBuilder.build();
        }

        private void doRequestAndWait(GoogleMapsRouteDetailsBuilder routeDetailsBuilder,
                GoogleMapsDirectionsRequest directionsRequest) {
            try {
                routeDetailsBuilder.response(directionsRequest.toDirectionsApiRequest(context).await());
            } catch (Exception e) {
                LOGGER.warn("Error during retrieving maps results", e);
                routeDetailsBuilder.error(e.getMessage());
            }
        }
    }

}
