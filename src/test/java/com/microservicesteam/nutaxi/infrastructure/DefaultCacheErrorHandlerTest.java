package com.microservicesteam.nutaxi.infrastructure;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.cache.Cache;

@RunWith(MockitoJUnitRunner.class)
public class DefaultCacheErrorHandlerTest {

    private DefaultCacheErrorHandler defaultCacheErrorHandler;

    @Mock
    private Cache cache;

    @Before
    public void init() {
        defaultCacheErrorHandler = new DefaultCacheErrorHandler();
    }

    @Test
    public void shouldPrintTheProblemAtClearError() {
        RuntimeException exception = spy(new RuntimeException());

        defaultCacheErrorHandler.handleCacheClearError(exception, cache);

        verify(exception).getMessage();
    }

    @Test
    public void shouldPrintTheProblemAtEvictError() {
        RuntimeException exception = spy(new RuntimeException());

        defaultCacheErrorHandler.handleCacheEvictError(exception, cache, null);

        verify(exception).getMessage();
    }

    @Test
    public void shouldPrintTheProblemAtGetError() {
        RuntimeException exception = spy(new RuntimeException());

        defaultCacheErrorHandler.handleCacheGetError(exception, cache, null);

        verify(exception).getMessage();
    }

    @Test
    public void shouldPrintTheProblemAtPutError() {
        RuntimeException exception = spy(new RuntimeException());

        defaultCacheErrorHandler.handleCachePutError(exception, cache, null, null);

        verify(exception).getMessage();
    }

}
