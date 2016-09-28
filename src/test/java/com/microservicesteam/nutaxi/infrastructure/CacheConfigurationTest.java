package com.microservicesteam.nutaxi.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { NutaxiRouteServiceApplicationTestConfiguration.class, CacheConfiguration.class })
public class CacheConfigurationTest {

    @Autowired
    private RedisTemplate<?, ?> redisTemplate;

    @Test
    public void shouldFireUpCaching() {
        assertThat(redisTemplate).isNotNull();
    }

}

@SpringBootConfiguration
@EnableAutoConfiguration
@TestPropertySource(locations = "classpath:bootstrap-test.properties")
class NutaxiRouteServiceApplicationTestConfiguration {

}