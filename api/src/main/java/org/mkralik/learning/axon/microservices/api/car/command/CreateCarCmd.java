package org.mkralik.learning.axon.microservices.api.car.command;

import org.mkralik.learning.axon.microservices.api.Booking;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.mkralik.learning.lra.axon.api.JoinLRA;
import org.mkralik.learning.lra.axon.api.LRAContext;

import java.net.URI;


@Value
@JoinLRA
public class CreateCarCmd {

    @TargetAggregateIdentifier
    private String id;
    @LRAContext
    private URI context;
    private String name;
    private Booking.BookingStatus status = Booking.BookingStatus.PROVISIONAL;
    private String type;
}
