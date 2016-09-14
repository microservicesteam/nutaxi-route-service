package com.microservicesteam.nutaxi.infrastructure;

import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("docker-aws")
public class RandomPortListener implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {

    private int port;

    @Override
    public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {
        this.port = event.getEmbeddedServletContainer().getPort();
    }

    public int getPort() {
        return port;
    }
}
