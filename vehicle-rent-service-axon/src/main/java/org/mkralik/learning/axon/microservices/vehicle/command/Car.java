package org.mkralik.learning.axon.microservices.vehicle.command;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.annotation.MetaDataValue;
import org.eclipse.microprofile.lra.annotation.ParticipantStatus;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.mkralik.learning.axon.microservices.api.Booking;
import org.mkralik.learning.axon.microservices.api.cinema.command.CreateTicketCmd;
import org.mkralik.learning.axon.microservices.api.vehicle.command.CreateCarCmd;
import org.mkralik.learning.axon.microservices.api.vehicle.event.ChangedCarStateEvent;
import org.mkralik.learning.axon.microservices.api.vehicle.event.CreatedCarEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.mkralik.learning.lra.axon.api.command.LRAAfterCommand;
import org.mkralik.learning.lra.axon.api.command.LRACompensateCommand;
import org.mkralik.learning.lra.axon.api.command.LRACompleteCommand;
import org.mkralik.learning.lra.axon.api.command.LRAStatusCommand;

import java.net.URI;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
@Slf4j
public class Car {

    @AggregateIdentifier
    private String id;
    private String name;
    private Booking.BookingStatus status;
    private String type;

    @CommandHandler
    public Car(CreateCarCmd cmd, @MetaDataValue(LRA.LRA_HTTP_CONTEXT_HEADER) URI context) {
        log.info("handling {}", cmd);
        apply(new CreatedCarEvent(cmd.getId(), cmd.getName(), cmd.getType()));
    }

    @CommandHandler
    public ParticipantStatus handle(LRACompensateCommand cmd) {
        log.debug("Someone wants to compensate {}", cmd);
        //do some validation
        apply(new ChangedCarStateEvent(cmd.getId(), Booking.BookingStatus.CANCELLED));
        return ParticipantStatus.Compensated;
    }

    @CommandHandler
    public ParticipantStatus handle(LRACompleteCommand cmd) {
        log.debug("Someone wants to complete {}", cmd);
        //do some validation
        apply(new ChangedCarStateEvent(cmd.getId(), Booking.BookingStatus.CONFIRMING));
        //for demonstrating status
        return ParticipantStatus.Completing;
    }

    @CommandHandler
    public ParticipantStatus handle(LRAStatusCommand cmd) {
        log.debug("++++++++++STATUS HANDLER! STATUS WAS CHANGED TO COMPLETE+++++");
        apply(new ChangedCarStateEvent(cmd.getId(), Booking.BookingStatus.CONFIRMED));
        return ParticipantStatus.Completed;
    }

    @CommandHandler
    public void handle(LRAAfterCommand cmd) {
        log.debug("++++++++++ The LRA is done. After method was invoked with cmd {}", cmd);
    }

    @EventSourcingHandler
    public void on(CreatedCarEvent evt) {
        log.debug("applying {}", evt);
        id = evt.getId();
        name = evt.getName();
        status = evt.getStatus();
        type = evt.getType();
    }

    @EventSourcingHandler
    public void on(ChangedCarStateEvent evt) {
        log.debug("applying {}", evt);
        status = evt.getStatus();
    }
}
