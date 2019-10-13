package org.mkralik.learning.axon.microservices.api.cinema.event;

import lombok.Value;
import org.mkralik.learning.axon.microservices.api.Booking;

@Value
public class CreatedTicketEvent {

    private String id;
    private String name;
    private Booking.BookingStatus status = Booking.BookingStatus.PROVISIONAL;
    private String type;
}
