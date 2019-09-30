package org.mkralik.learning.axon.microservices.car;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(WadlResource.class);
        register(CarEndpoints.class);
    }
}

