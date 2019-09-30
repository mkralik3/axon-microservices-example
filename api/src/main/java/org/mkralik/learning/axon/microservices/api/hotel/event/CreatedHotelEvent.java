package org.mkralik.learning.axon.microservices.api.hotel.event;

import lombok.Value;
import org.mkralik.learning.axon.microservices.api.BookingStatus;

@Value
public class CreatedHotelEvent {

    private String id;
    private String name;
    private BookingStatus status = BookingStatus.PROVISIONAL;
    private String type;
}
