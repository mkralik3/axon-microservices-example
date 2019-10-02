package org.mkralik.learning.axon.microservices.hotel;

import io.narayana.lra.client.NarayanaLRAClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class ConfigClass {
    @Bean
    @Primary
    public NarayanaLRAClient NarayanaLRAClient() throws URISyntaxException {
        return new NarayanaLRAClient();
    }
}
