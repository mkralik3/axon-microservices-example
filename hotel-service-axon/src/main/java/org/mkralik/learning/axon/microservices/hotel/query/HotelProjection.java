package org.mkralik.learning.axon.microservices.hotel.query;

import org.mkralik.learning.axon.microservices.api.Booking;
import org.mkralik.learning.axon.microservices.api.hotel.event.ChangedHotelStateEvent;
import org.mkralik.learning.axon.microservices.api.hotel.event.CreatedHotelEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.mkralik.learning.axon.microservices.api.hotel.query.AllHotelBookingSummaryQuery;
import org.mkralik.learning.axon.microservices.api.hotel.query.HotelBookingSummaryQuery;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class HotelProjection {

    private Map<String, Booking> bookings = new HashMap<>();

    @EventHandler
    public void on(CreatedHotelEvent evt){
        log.debug("projecting CreatedHotelEvent {}", evt);
        bookings.put(evt.getId(), new Booking(evt.getId().toString(), evt.getName(), evt.getType(), evt.getStatus(), evt.getDetails()));
    }

    @EventHandler
    public void on(ChangedHotelStateEvent evt){
        log.debug("projecting ChangedHotelStateEvent {}", evt);
        bookings.get(evt.getId()).setStatus(evt.getStatus());
    }

    @QueryHandler
    public Booking handle(HotelBookingSummaryQuery qry){
        return bookings.get(qry.getId());
    }

    @QueryHandler
    public List<Booking> handle(AllHotelBookingSummaryQuery qry){
        return new ArrayList<Booking>(bookings.values());
    }
}
