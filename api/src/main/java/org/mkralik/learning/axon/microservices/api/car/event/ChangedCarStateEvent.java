package org.mkralik.learning.axon.microservices.api.car.event;

import org.mkralik.learning.axon.microservices.api.Booking;
import lombok.Value;

@Value
public class ChangedCarStateEvent {
    private String id;
    private Booking.BookingStatus status;
}
