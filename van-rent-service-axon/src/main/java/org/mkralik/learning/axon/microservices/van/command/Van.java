package org.mkralik.learning.axon.microservices.van.command;

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
import org.mkralik.learning.axon.microservices.api.van.command.CreateVanCmd;
import org.mkralik.learning.axon.microservices.api.van.event.ChangedVanStateEvent;
import org.mkralik.learning.axon.microservices.api.van.event.CreatedVanEvent;
import org.mkralik.learning.lra.axon.api.command.LRACompensateCommand;
import org.mkralik.learning.lra.axon.api.command.LRACompleteCommand;
import org.mkralik.learning.lra.axon.api.command.LRAStatusCommand;

import java.net.URI;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
@Slf4j
public class Van {

    @AggregateIdentifier
    private String id;
    private String name;
    private Booking.BookingStatus status;
    private String type;

    @CommandHandler
    public Van(CreateVanCmd cmd, @MetaDataValue(LRA.LRA_HTTP_CONTEXT_HEADER) URI context){
        log.info("handling  Van {}", cmd);
        apply(new CreatedVanEvent(cmd.getId(), cmd.getName(), cmd.getType()));
    }

    @CommandHandler
    public void handle(LRACompensateCommand cmd){
        log.debug("Someone wants to compensate Van {}", cmd);
        //do some validation
        apply(new ChangedVanStateEvent(cmd.getId(), Booking.BookingStatus.CANCELLED));
    }

    @CommandHandler
    public ParticipantStatus handle(LRACompleteCommand cmd){
        log.debug("Someone wants to complete  Van{}", cmd);
        //do some validation
        apply(new ChangedVanStateEvent(cmd.getId(), Booking.BookingStatus.CONFIRMING));
        //for demonstrating status
        return ParticipantStatus.Completing;
    }

    @CommandHandler
    public ParticipantStatus handle(LRAStatusCommand cmd){
        log.debug("++++++++++Van STATUS HANDLER! STATUS WAS CHANGED TO COMPLETE+++++");
        apply(new ChangedVanStateEvent(cmd.getId(), Booking.BookingStatus.CONFIRMED));
        return ParticipantStatus.Completed;
    }


    @EventSourcingHandler
    public void on(CreatedVanEvent evt){
        log.debug("applying {}", evt);
        id = evt.getId();
        name = evt.getName();
        status = evt.getStatus();
        type = evt.getType();
    }

    @EventSourcingHandler
    public void on(ChangedVanStateEvent evt){
        log.debug("applying {}", evt);
        status = evt.getStatus();
    }
}
