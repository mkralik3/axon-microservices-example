package org.mkralik.learning.axon.microservices.cinema.query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.mkralik.learning.axon.microservices.api.Booking;
import org.mkralik.learning.axon.microservices.api.cinema.event.ChangedTicketStateEvent;
import org.mkralik.learning.axon.microservices.api.cinema.event.CreatedTicketEvent;
import org.mkralik.learning.axon.microservices.api.cinema.query.AllTicketBookingSummaryQuery;
import org.mkralik.learning.axon.microservices.api.cinema.query.TicketBookingSummaryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class TicketProjection {

    @Autowired
    private CommandGateway cmdGateway;

    private Map<String, Booking> bookings = new HashMap<>();

    @EventHandler
    public void on(CreatedTicketEvent evt){
        log.debug("projecting CreatedCinemaEvent {}", evt);
        bookings.put(evt.getId(), new Booking(evt.getId(), evt.getName(), evt.getStatus(), evt.getType(), null));
    }

    @EventHandler
    public void on(ChangedTicketStateEvent evt){
        log.debug("projecting ChangedCinemaStateEvent {}", evt);
        bookings.get(evt.getId()).setStatus(evt.getStatus());
    }

    @QueryHandler
    public Booking handle(TicketBookingSummaryQuery qry){
        return bookings.get(qry.getId());
    }

    @QueryHandler
    public List<Booking> handle(AllTicketBookingSummaryQuery qry){

        return new ArrayList<Booking>(bookings.values());
    }
}
