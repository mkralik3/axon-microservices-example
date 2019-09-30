package org.mkralik.learning.axon.microservices.hotel;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(WadlResource.class);
        register(HotelEndpoints.class);
        register(io.narayana.lra.filter.ClientLRARequestFilter.class);
        register(io.narayana.lra.filter.ClientLRAResponseFilter.class);
        register(io.narayana.lra.filter.FilterRegistration.class);
        register(io.narayana.lra.filter.ServerLRAFilter.class);
    }
}
