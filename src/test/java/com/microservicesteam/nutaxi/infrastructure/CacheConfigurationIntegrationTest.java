package com.microservicesteam.nutaxi.infrastructure;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { NutaxiRouteServiceApplicationTestConfiguration.class, CacheConfiguration.class })
public abstract class CacheConfigurationIntegrationTest {

}
