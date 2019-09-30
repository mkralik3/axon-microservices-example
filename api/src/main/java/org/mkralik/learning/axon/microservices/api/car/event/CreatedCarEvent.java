package org.mkralik.learning.axon.microservices.api.car.event;

import org.mkralik.learning.axon.microservices.api.BookingStatus;
import lombok.Value;

@Value
public class CreatedCarEvent {

    private String id;
    private String name;
    private BookingStatus status = BookingStatus.PROVISIONAL;
    private String type;
}
