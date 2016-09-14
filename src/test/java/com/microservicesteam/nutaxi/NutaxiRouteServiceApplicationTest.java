package com.microservicesteam.nutaxi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.InetUtilsProperties;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;

import com.microservicesteam.nutaxi.infrastructure.RandomPortListener;

@RunWith(MockitoJUnitRunner.class)
public class NutaxiRouteServiceApplicationTest { 
    
    private InetUtils inetUtils = new InetUtils(new InetUtilsProperties());
    
    @Mock
    private RandomPortListener portListener;
    
    private NutaxiRouteServiceApplication underTest;
    
    @Test
    public void shouldConfigureEurekaInstanceConfigBean() {
        when(portListener.getPort()).thenReturn(12345);
        
        underTest = new NutaxiRouteServiceApplication();
        
        EurekaInstanceConfigBean eurekaInstanceConfig = underTest.eurekaInstanceConfig(inetUtils, portListener);
        
        assertThat(eurekaInstanceConfig.getNonSecurePort()).isEqualTo(12345);
    }

}
