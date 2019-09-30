package org.mkralik.learning.axon.microservices.hotel;

import io.narayana.lra.client.NarayanaLRAClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class ConfigClass {
    @Bean
    public NarayanaLRAClient NarayanaLRAClient() throws URISyntaxException {
        NarayanaLRAClient.setDefaultCoordinatorEndpoint(new URI("http://localhost:8080"));
        return new NarayanaLRAClient();
    }
}
