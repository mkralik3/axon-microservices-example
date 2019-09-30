package org.mkralik.learning.axon.microservices.api.hotel.event;

import org.mkralik.learning.axon.microservices.api.BookingStatus;
import lombok.Value;

@Value
public class ChangedHotelStateEvent {
    private String id;
    private BookingStatus status;
}
