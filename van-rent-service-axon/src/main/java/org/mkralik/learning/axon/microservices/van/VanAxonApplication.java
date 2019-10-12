package org.mkralik.learning.axon.microservices.van;

import org.mkralik.learning.lra.axon.api.EnableLraAxonConnectorModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableLraAxonConnectorModule
public class VanAxonApplication {
    public static void main(String[] args) {
        SpringApplication.run(VanAxonApplication.class, args);
    }
}
