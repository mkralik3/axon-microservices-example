package org.mkralik.learning.axon.microservices.car.query;

import org.mkralik.learning.axon.microservices.api.Booking;
import org.mkralik.learning.axon.microservices.api.car.query.AllCarBookingSummaryQuery;
import org.mkralik.learning.axon.microservices.api.car.query.CarBookingSummaryQuery;
import org.mkralik.learning.axon.microservices.api.car.event.ChangedCarStateEvent;
import org.mkralik.learning.axon.microservices.api.car.event.CreatedCarEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class CarProjection {

    private Map<String, Booking> bookings = new HashMap<>();

    @EventHandler
    public void on(CreatedCarEvent evt){
        log.debug("projecting CreatedCarEvent {}", evt);
        bookings.put(evt.getId(), new Booking(evt.getId(), evt.getName(), evt.getStatus(), evt.getType(), evt.getDetails()));
    }

    @EventHandler
    public void on(ChangedCarStateEvent evt){
        log.debug("projecting ChangedCarStateEvent {}", evt);
        bookings.get(evt.getId()).setStatus(evt.getStatus());
    }

    @QueryHandler
    public Booking handle(CarBookingSummaryQuery qry){
        return bookings.get(qry.getId());
    }

    @QueryHandler
    public List<Booking> handle(AllCarBookingSummaryQuery qry){

        return new ArrayList<Booking>(bookings.values());
    }
}
