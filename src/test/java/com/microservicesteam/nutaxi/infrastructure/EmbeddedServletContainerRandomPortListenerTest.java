package com.microservicesteam.nutaxi.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.context.embedded.EmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;

import com.microservicesteam.nutaxi.infrastructure.EmbeddedServletContainerRandomPortListener;

@RunWith(MockitoJUnitRunner.class)
public class EmbeddedServletContainerRandomPortListenerTest {

    @Mock
    private EmbeddedServletContainerInitializedEvent mockEmbeddedServletContainerInitializedEvent;

    @Mock
    private EmbeddedServletContainer mockEmbeddedServletContainer;

    private EmbeddedServletContainerRandomPortListener underTest;

    @Before
    public void init() {
        underTest = new EmbeddedServletContainerRandomPortListener();
    }

    @Test
    public void shouldReturnContainerPortAfterContainerInitialisationEvent() {
        int testPort = 47253;
        when(mockEmbeddedServletContainer.getPort()).thenReturn(testPort);
        when(mockEmbeddedServletContainerInitializedEvent.getEmbeddedServletContainer()).thenReturn(mockEmbeddedServletContainer);

        underTest.onApplicationEvent(mockEmbeddedServletContainerInitializedEvent);

        assertThat(underTest.getPort()).isEqualTo(testPort);
    }

}
