package org.mkralik.learning.axon.microservices.hotel;

import io.narayana.lra.client.internal.proxy.nonjaxrs.LRAParticipantRegistry;
import org.glassfish.jersey.internal.inject.AbstractBinder;
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
        //In case you want to use full AxonLra extension with JAX RS service. When you want only propagate the context, you don't need this endpoint which is used for communication with LRA coordinator.
        register(org.mkralik.learning.lra.axon.rest.AxonLraEndpointsJaxRS.class);
        register(new AbstractBinder(){
            @Override
            protected void configure() {
                bind(LRAParticipantRegistry.class)
                        .to(LRAParticipantRegistry.class);
            }
        });
    }
}
