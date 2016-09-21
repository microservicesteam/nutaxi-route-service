package com.microservicesteam.nutaxi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.google.maps.GeoApiContext;
import com.microservicesteam.nutaxi.infrastructure.EmbeddedServletContainerRandomPortListener;
import com.microservicesteam.nutaxi.infrastructure.KryoRedisSerializer;
import com.microservicesteam.nutaxi.route.googlemaps.GoogleMapsRouteDetails;
import com.netflix.appinfo.AmazonInfo;

@EnableDiscoveryClient
@SpringBootApplication
@EnableCaching
public class NutaxiRouteServiceApplication extends CachingConfigurerSupport {

    @Value("${google-maps.api-key}")
    private String apiKey;

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Bean
    public GeoApiContext geoApiContext() {
        return new GeoApiContext().setApiKey(apiKey);
    }

    @Bean
    public RedisTemplate template() {
        RedisTemplate template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setDefaultSerializer(new KryoRedisSerializer(GoogleMapsRouteDetails.class));

        return template;
    }

    @Override
    public CacheManager cacheManager() {
        return new RedisCacheManager(template());
    }

    @Bean
    @Profile("docker-aws")
    public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils, EmbeddedServletContainerRandomPortListener portListener) {
        EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
        AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("eureka");
        config.setDataCenterInfo(info);
        info.getMetadata().put(AmazonInfo.MetaDataKey.publicHostname.getName(), info.get(AmazonInfo.MetaDataKey.publicIpv4));
        config.setInstanceId(info.get(AmazonInfo.MetaDataKey.instanceId));
        config.setHostname(info.get(AmazonInfo.MetaDataKey.publicHostname));
        config.setIpAddress(info.get(AmazonInfo.MetaDataKey.publicIpv4));
        config.setNonSecurePort(portListener.getPort());
        return config;
    }

    public static void main(String[] args) {
        SpringApplication.run(NutaxiRouteServiceApplication.class, args);
    }

}
