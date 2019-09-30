package org.mkralik.learning.axon.microservices.api.hotel.command;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.mkralik.learning.axon.microservices.api.BookingStatus;

@Value
public class CreateHotelCmd {

    @TargetAggregateIdentifier
    private String id;
    private String name;
    private BookingStatus status = BookingStatus.PROVISIONAL;
    private String type;
}
