package org.mkralik.learning.axon.microservices.api.hotel.event;

import lombok.Value;
import org.mkralik.learning.axon.microservices.api.Booking;

import java.util.List;

@Value
public class CreatedHotelEvent {

    private String id;
    private String name;
    private Booking.BookingStatus status = Booking.BookingStatus.PROVISIONAL;
    private String type;
    private List<Booking> details;
}
