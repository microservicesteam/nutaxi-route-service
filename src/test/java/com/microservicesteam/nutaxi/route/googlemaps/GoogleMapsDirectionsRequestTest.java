package com.microservicesteam.nutaxi.route.googlemaps;

import org.junit.Test;
import org.meanbean.test.BeanTester;

public class GoogleMapsDirectionsRequestTest {

    private final BeanTester beanTester = new BeanTester();

    @Test
    public void test() {
        beanTester.testBean(GoogleMapsDirectionsRequest.class);
    }

}
