package org.mkralik.learning.axon.microservices.api.car.command;

import org.mkralik.learning.axon.microservices.model.Booking;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CompensateCarCmd {
    @TargetAggregateIdentifier
    private String id;
    private Booking.BookingStatus status = Booking.BookingStatus.CANCELLED;
}
