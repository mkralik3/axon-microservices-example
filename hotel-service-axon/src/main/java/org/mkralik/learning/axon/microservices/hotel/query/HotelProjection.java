package org.mkralik.learning.axon.microservices.hotel.query;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.mkralik.learning.axon.microservices.api.Booking;
import org.mkralik.learning.axon.microservices.api.cinema.event.ChangedTicketStateEvent;
import org.mkralik.learning.axon.microservices.api.cinema.query.TicketBookingSummaryQuery;
import org.mkralik.learning.axon.microservices.api.vehicle.event.ChangedCarStateEvent;
import org.mkralik.learning.axon.microservices.api.vehicle.event.ChangedVanStateEvent;
import org.mkralik.learning.axon.microservices.api.vehicle.query.CarBookingSummaryQuery;
import org.mkralik.learning.axon.microservices.api.hotel.event.ChangedHotelStateEvent;
import org.mkralik.learning.axon.microservices.api.hotel.event.CreatedHotelEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.mkralik.learning.axon.microservices.api.hotel.query.AllHotelBookingSummaryQuery;
import org.mkralik.learning.axon.microservices.api.hotel.query.HotelBookingSummaryQuery;
import org.mkralik.learning.axon.microservices.api.vehicle.query.VanBookingSummaryQuery;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class HotelProjection {

    private Map<String, Booking> bookings = new HashMap<>();

    @Inject
    private QueryGateway queryGateway;

    @EventHandler
    public void on(CreatedHotelEvent evt){
        log.debug("projecting created hotel {}", evt);
        //add Sub Bookings to the Root Booking
        List<Booking> subBookings = new ArrayList<>();
        for (String subBookingID : evt.getSubBookingsId()) {
            Booking subBooking = null;
            subBooking = queryGateway.query(new CarBookingSummaryQuery(subBookingID),
                    ResponseTypes.instanceOf(Booking.class))
                    .join();
            if(subBooking==null){
                subBooking = queryGateway.query(new VanBookingSummaryQuery(subBookingID),
                        ResponseTypes.instanceOf(Booking.class))
                        .join();
            }if(subBooking==null){
                subBooking = queryGateway.query(new TicketBookingSummaryQuery(subBookingID),
                        ResponseTypes.instanceOf(Booking.class))
                        .join();
            }
            subBookings.add(subBooking);
        }
        bookings.put(evt.getId(), new Booking(evt.getId(), evt.getName(), evt.getStatus(), evt.getType(), subBookings));
    }

    @EventHandler
    public void on(ChangedCarStateEvent evt){
        log.debug("change car state {}", evt);
        updateSubBookingToBooking(evt.getId(), evt.getStatus());
    }

    @EventHandler
    public void on(ChangedVanStateEvent evt){
        log.debug("projecting change van state {}", evt);
        updateSubBookingToBooking(evt.getId(), evt.getStatus());
    }

    @EventHandler
    public void on(ChangedTicketStateEvent evt){
        log.debug("projecting change ticket state {}", evt);
        updateSubBookingToBooking(evt.getId(), evt.getStatus());
    }

    private void updateSubBookingToBooking(String subBookingId, Booking.BookingStatus subBookingStatus){

        List<Booking> hotelsForUpdate = bookings.values().stream().filter(hotel -> hotel.getDetails().stream()
                .anyMatch(car -> car.getId().equals(subBookingId)))
                .collect(Collectors.toList());

        for (Booking hotel : hotelsForUpdate) {
            hotel.getDetails().stream()
                    .filter(car -> car.getId().equals(subBookingId))
                    .findAny()
                    .ifPresent(car -> car.setStatus(subBookingStatus));
        }
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
