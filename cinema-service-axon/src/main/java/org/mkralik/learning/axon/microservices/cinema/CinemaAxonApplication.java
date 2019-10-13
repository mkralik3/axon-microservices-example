package org.mkralik.learning.axon.microservices.cinema;

import org.mkralik.learning.lra.axon.api.EnableLraAxonConnectorModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableLraAxonConnectorModule
public class CinemaAxonApplication {
    public static void main(String[] args) {
        SpringApplication.run(CinemaAxonApplication.class, args);
    }
}