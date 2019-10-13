package org.mkralik.learning.axon.microservices.vehicle.query;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.mkralik.learning.axon.microservices.api.Booking;
import org.mkralik.learning.axon.microservices.api.vehicle.ChangedVanStateEvent;
import org.mkralik.learning.axon.microservices.api.vehicle.CreatedVanEvent;
import org.mkralik.learning.axon.microservices.api.vehicle.AllVanBookingSummaryQuery;
import org.mkralik.learning.axon.microservices.api.vehicle.VanBookingSummaryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class VanProjection {

    @Autowired
    private CommandGateway cmdGateway;

    private Map<String, Booking> bookings = new HashMap<>();

    @EventHandler
    public void on(CreatedVanEvent evt) {
        log.debug("projecting CreatedVanEvent {}", evt);
        bookings.put(evt.getId(), new Booking(evt.getId(), evt.getName(), evt.getStatus(), evt.getType(), null));
    }

    @EventHandler
    public void on(ChangedVanStateEvent evt) {
        log.debug("projecting ChangedVanStateEvent {}", evt);
        bookings.get(evt.getId()).setStatus(evt.getStatus());
    }

    @QueryHandler
    public Booking handle(VanBookingSummaryQuery qry) {
        return bookings.get(qry.getId());
    }

    @QueryHandler
    public List<Booking> handle(AllVanBookingSummaryQuery qry) {

        return new ArrayList<>(bookings.values());
    }
}
