package org.mkralik.learning.axon.microservices.api.car.event;

import lombok.Value;
import org.mkralik.learning.axon.microservices.api.Booking;

import java.util.List;

@Value
public class CreatedCarEvent {

    private String id;
    private String name;
    private Booking.BookingStatus status = Booking.BookingStatus.PROVISIONAL;
    private String type;
}
