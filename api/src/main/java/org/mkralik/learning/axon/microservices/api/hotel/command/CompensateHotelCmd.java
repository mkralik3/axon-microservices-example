package org.mkralik.learning.axon.microservices.api.hotel.command;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.mkralik.learning.axon.microservices.model.Booking;

@Value
public class CompensateHotelCmd {
    @TargetAggregateIdentifier
    private String id;
    private Booking.BookingStatus status = Booking.BookingStatus.CANCELLED;
}
