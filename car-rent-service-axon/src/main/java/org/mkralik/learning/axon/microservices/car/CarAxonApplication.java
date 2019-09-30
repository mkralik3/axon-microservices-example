package org.mkralik.learning.axon.microservices.car;

import org.mkralik.learning.lra.axon.api.EnableLraAxonConnectorModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableLraAxonConnectorModule
public class CarAxonApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarAxonApplication.class, args);
    }
}
