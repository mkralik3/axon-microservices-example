package org.mkralik.learning.axon.microservices.cinema.command;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.messaging.annotation.MetaDataValue;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.eclipse.microprofile.lra.annotation.ParticipantStatus;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.mkralik.learning.axon.microservices.api.Booking;
import org.mkralik.learning.axon.microservices.api.cinema.command.CreateTicketCmd;
import org.mkralik.learning.axon.microservices.api.cinema.event.ChangedTicketStateEvent;
import org.mkralik.learning.axon.microservices.api.cinema.event.CreatedTicketEvent;
import org.mkralik.learning.lra.axon.api.command.LRACompensateCommand;
import org.mkralik.learning.lra.axon.api.command.LRACompleteCommand;

import java.net.URI;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
@Slf4j
public class Ticket {

    @AggregateIdentifier
    private String id;
    private String name;
    private Booking.BookingStatus status;
    private String type;

    @CommandHandler
    public Ticket(CreateTicketCmd cmd, @MetaDataValue(LRA.LRA_HTTP_CONTEXT_HEADER) URI context){
        apply(new CreatedTicketEvent(cmd.getId(), cmd.getName(), cmd.getType()));
    }

    @CommandHandler
    public ParticipantStatus handle(LRACompensateCommand cmd){
        log.debug("Someone wants to compensate ticket {}", cmd);
        apply(new ChangedTicketStateEvent(cmd.getId(), Booking.BookingStatus.CANCELLED));
        return ParticipantStatus.Compensated;
    }

    @CommandHandler
    public ParticipantStatus handle(LRACompleteCommand cmd){
        log.debug("Someone wants to complete ticket {}", cmd);
        apply(new ChangedTicketStateEvent(cmd.getId(), Booking.BookingStatus.CONFIRMED));
        return ParticipantStatus.Completed;
    }

    @EventSourcingHandler
    public void on(CreatedTicketEvent evt){
        log.debug("applying {}", evt);
        id = evt.getId();
        name = evt.getName();
        status = evt.getStatus();
        type = evt.getType();
    }

    @EventSourcingHandler
    public void on(ChangedTicketStateEvent evt){
        log.debug("applying {}", evt);
        status = evt.getStatus();
    }
}
