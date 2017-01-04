package com.microservicesteam.nutaxi.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("disabled-cache")
public class DisabledCacheConfigurationIntegrationTest extends CacheConfigurationIntegrationTest {

    @Value("${nutaxi.route.service.cache.enabled}")
    private boolean isCacheEnabled;

    @Test
    public void shouldLoadDisabledCacheManager() {
        assertThat(isCacheEnabled).isFalse();
    }

}
