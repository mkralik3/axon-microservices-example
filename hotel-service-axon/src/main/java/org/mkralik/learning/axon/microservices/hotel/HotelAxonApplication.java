package org.mkralik.learning.axon.microservices.hotel;

import org.mkralik.learning.lra.axon.interceptor.EnableLraAxonConnectorModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableLraAxonConnectorModule
public class HotelAxonApplication {
    public static void main(String[] args) {
        SpringApplication.run(HotelAxonApplication.class, args);
    }
}
