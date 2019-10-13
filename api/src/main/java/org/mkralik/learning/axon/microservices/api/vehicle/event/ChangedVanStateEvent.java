package org.mkralik.learning.axon.microservices.api.vehicle.event;

import lombok.Value;
import org.mkralik.learning.axon.microservices.api.Booking;

@Value
public class ChangedVanStateEvent {
    private String id;
    private Booking.BookingStatus status;
}
