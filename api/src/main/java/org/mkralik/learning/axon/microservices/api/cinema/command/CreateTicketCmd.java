package org.mkralik.learning.axon.microservices.api.cinema.command;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.mkralik.learning.axon.microservices.api.Booking;
import org.mkralik.learning.lra.axon.api.annotation.JoinLRA;
import org.mkralik.learning.lra.axon.api.annotation.LRAContext;

import java.net.URI;

@Value
@JoinLRA
public class CreateTicketCmd {

    @TargetAggregateIdentifier
    private String id;
    // can be explicitly stated
    @LRAContext
    private URI context;
    private String name;
    private Booking.BookingStatus status = Booking.BookingStatus.PROVISIONAL;
    private String type;
}
