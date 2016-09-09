package com.microservicesteam.nutaxi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import com.google.maps.GeoApiContext;
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

}
