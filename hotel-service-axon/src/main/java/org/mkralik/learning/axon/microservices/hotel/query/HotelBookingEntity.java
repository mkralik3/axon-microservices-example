package org.mkralik.learning.axon.microservices.hotel.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.mkralik.learning.axon.microservices.api.Booking;

import java.util.List;

@Data
@AllArgsConstructor
public class HotelBookingEntity {
    private String id;
    private String name;
    private Booking.BookingStatus status;
    private String type;
    private List<String> subBookingId;
}
