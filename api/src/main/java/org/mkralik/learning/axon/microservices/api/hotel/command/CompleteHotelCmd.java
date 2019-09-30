package org.mkralik.learning.axon.microservices.api.hotel.command;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.mkralik.learning.axon.microservices.api.Booking;

@Value
public class CompleteHotelCmd {
    @TargetAggregateIdentifier
    private String id;
    private Booking.BookingStatus status = Booking.BookingStatus.CONFIRMED;
}
