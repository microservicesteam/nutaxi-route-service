package com.microservicesteam.nutaxi.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class CacheConfiguration extends CachingConfigurerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheConfiguration.class);

    @Value("${nutaxi.route.service.cache.enabled}")
    private boolean isCacheEnabled;

    @Override
    public CacheErrorHandler errorHandler() {
        return new DefaultCacheErrorHandler();
    }

    @Override
    public CacheManager cacheManager() {
        LOGGER.info("Cache enabled: {}", isCacheEnabled);
        if (isCacheEnabled) {
            return super.cacheManager();
        } else {
            return new NoOpCacheManager();
        }
    }

}
