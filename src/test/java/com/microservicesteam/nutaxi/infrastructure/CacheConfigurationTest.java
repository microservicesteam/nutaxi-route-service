package com.microservicesteam.nutaxi.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import redis.embedded.RedisServer;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { NutaxiRouteServiceApplicationTestConfiguration.class, CacheConfiguration.class })
public class CacheConfigurationTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

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

        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        opsForValue.set("testKey", "testValue");

        String result = opsForValue.get("testKey");
        assertThat(result).isEqualTo("testValue");
    }

}

@SpringBootConfiguration
@EnableAutoConfiguration(exclude = { CloudConfiguration.class })
class NutaxiRouteServiceApplicationTestConfiguration {
}