package org.mkralik.learning.axon.microservices.api.hotel.command;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.mkralik.learning.axon.microservices.api.BookingStatus;

@Value
public class CompleteHotelCmd {
    @TargetAggregateIdentifier
    private String id;
    private BookingStatus status = BookingStatus.CONFIRMED;
}
