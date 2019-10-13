package org.mkralik.learning.axon.microservices.vehicle;

import org.mkralik.learning.lra.axon.interceptor.EnableLraAxonConnectorModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableLraAxonConnectorModule
public class VehicleAxonApplication {
    public static void main(String[] args) {
        SpringApplication.run(VehicleAxonApplication.class, args);
    }
}
