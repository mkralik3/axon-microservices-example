package org.mkralik.learning.axon.microservices.api.car.command;

import org.mkralik.learning.axon.microservices.api.BookingStatus;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CreateCarCmd {

    @TargetAggregateIdentifier
    private String id;
    private String name;
    private BookingStatus status = BookingStatus.PROVISIONAL;
    private String type;
}
