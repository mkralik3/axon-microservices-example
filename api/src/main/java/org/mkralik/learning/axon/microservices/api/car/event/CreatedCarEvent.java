package org.mkralik.learning.axon.microservices.api.car.event;

import org.mkralik.learning.axon.microservices.model.Booking;
import lombok.Value;

@Value
public class CreatedCarEvent {

    private String id;
    private String name;
    private Booking.BookingStatus status = Booking.BookingStatus.PROVISIONAL;
    private String type;
    private Booking[] details = null;
}
