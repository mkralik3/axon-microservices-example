package org.mkralik.learning.axon.microservices.api.hotel.event;

import org.mkralik.learning.axon.microservices.api.Booking;
import lombok.Value;

@Value
public class ChangedHotelStateEvent {
    private String id;
    private Booking.BookingStatus status;
}
