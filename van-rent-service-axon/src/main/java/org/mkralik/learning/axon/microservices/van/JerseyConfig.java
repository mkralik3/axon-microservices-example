package org.mkralik.learning.axon.microservices.van;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(WadlResource.class);
        register(VanEndpoints.class);
        //In case you want to use full AxonLra extension with JAX RS service. When you want only propagate the context, you don't need this endpoint which is used for communication with LRA coordinator.
        register(org.mkralik.learning.lra.axon.rest.AxonLraEndpointsJaxRS.class);
    }
}
