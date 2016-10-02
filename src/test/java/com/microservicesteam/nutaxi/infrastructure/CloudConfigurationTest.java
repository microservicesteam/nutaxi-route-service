package com.microservicesteam.nutaxi.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.InetUtilsProperties;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;

@RunWith(MockitoJUnitRunner.class)
public class CloudConfigurationTest {

    private CloudConfiguration underTest;

    @Mock
    private EmbeddedServletContainerRandomPortListener portListener;

    @Before
    public void init() {
        underTest = new CloudConfiguration();
    }

    @Test
    public void shouldConfigureEurekaInstanceConfigBean() {
        when(portListener.getPort()).thenReturn(12345);

        EurekaInstanceConfigBean eurekaInstanceConfig = underTest.eurekaInstanceConfig(getINetUtils(), portListener);

        assertThat(eurekaInstanceConfig.getNonSecurePort()).isEqualTo(12345);
    }

    private static InetUtils getINetUtils() {
        return new InetUtils(new InetUtilsProperties());
    }

}
