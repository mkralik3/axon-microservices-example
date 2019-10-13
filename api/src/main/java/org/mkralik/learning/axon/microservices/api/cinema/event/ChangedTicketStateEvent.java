package org.mkralik.learning.axon.microservices.api.cinema.event;

import org.mkralik.learning.axon.microservices.api.Booking;
import lombok.Value;

@Value
public class ChangedTicketStateEvent {
    private String id;
    private Booking.BookingStatus status;
}
