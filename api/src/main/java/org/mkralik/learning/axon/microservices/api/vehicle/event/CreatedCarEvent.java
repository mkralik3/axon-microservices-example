package org.mkralik.learning.axon.microservices.api.vehicle.event;

import lombok.Value;
import org.mkralik.learning.axon.microservices.api.Booking;

@Value
public class CreatedCarEvent {

    private String id;
    private String name;
    private Booking.BookingStatus status = Booking.BookingStatus.PROVISIONAL;
    private String type;
}
