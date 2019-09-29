package org.mkralik.learning.axon.microservices.api.hotel.event;

import lombok.Value;
import org.mkralik.learning.axon.microservices.model.Booking;

@Value
public class CreatedHotelEvent {

    private String id;
    private String name;
    private Booking.BookingStatus status = Booking.BookingStatus.PROVISIONAL;
    private String type;
    private Booking[] details = null;
}
