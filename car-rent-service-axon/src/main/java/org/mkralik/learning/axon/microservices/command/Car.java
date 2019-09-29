package org.mkralik.learning.axon.microservices.command;

import org.mkralik.learning.axon.microservices.api.car.command.CompensateCarCmd;
import org.mkralik.learning.axon.microservices.api.car.command.CompleteCarCmd;
import org.mkralik.learning.axon.microservices.model.Booking;
import org.mkralik.learning.axon.microservices.api.car.command.CreateCarCmd;
import org.mkralik.learning.axon.microservices.api.car.event.ChangedCarStateEvent;
import org.mkralik.learning.axon.microservices.api.car.event.CreatedCarEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

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
    private Booking[] details;

    @CommandHandler
    public Car(CreateCarCmd cmd){
        log.debug("handling {}", cmd);
        apply(new CreatedCarEvent(cmd.getId(), cmd.getName(), cmd.getType()));
    }

    @CommandHandler
    public void handle(CompleteCarCmd cmd){
        log.debug("handling {}", cmd);
        //do some validation
        apply(new ChangedCarStateEvent(cmd.getId(), cmd.getStatus()));
    }

    @CommandHandler
    public void handle(CompensateCarCmd cmd){
        log.debug("handling {}", cmd);
        //do some validation
        apply(new ChangedCarStateEvent(cmd.getId(), cmd.getStatus()));
    }

    @EventSourcingHandler
    public void on(CreatedCarEvent evt){
        log.debug("applying {}", evt);
        id = evt.getId();
        name = evt.getName();
        status = evt.getStatus();
        type = evt.getType();
        details = evt.getDetails();
    }

    @EventSourcingHandler
    public void on(ChangedCarStateEvent evt){
        log.debug("applying {}", evt);
        status = evt.getStatus();
    }
}
