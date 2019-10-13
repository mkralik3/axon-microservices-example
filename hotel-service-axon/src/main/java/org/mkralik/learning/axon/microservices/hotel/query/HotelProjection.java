package org.mkralik.learning.axon.microservices.hotel.query;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.mkralik.learning.axon.microservices.api.Booking;
import org.mkralik.learning.axon.microservices.api.cinema.query.TicketBookingSummaryQuery;
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

@Component
@RequiredArgsConstructor
@Slf4j
public class HotelProjection {

    private Map<String, HotelBookingEntity> bookings = new HashMap<>();

    @Inject
    private QueryGateway queryGateway;

    @EventHandler
    public void on(CreatedHotelEvent evt) {
        log.debug("projecting created hotel {}", evt);
        bookings.put(evt.getId(), new HotelBookingEntity(evt.getId(), evt.getName(), evt.getStatus(), evt.getType(), evt.getSubBookingsId()));
    }

    @EventHandler
    public void on(ChangedHotelStateEvent evt) {
        log.debug("projecting ChangedHotelStateEvent {}", evt);
        bookings.get(evt.getId()).setStatus(evt.getStatus());
    }

    @QueryHandler
    public Booking handle(HotelBookingSummaryQuery qry) {
        HotelBookingEntity hotelBookingEntity = bookings.get(qry.getId());
        if (hotelBookingEntity == null) {
            return null;
        }
        return transformToGeneralBooking(hotelBookingEntity);
    }

    @QueryHandler
    public List<Booking> handle(AllHotelBookingSummaryQuery qry) {
        List<Booking> result = new ArrayList<>();
        for (HotelBookingEntity hotelBookingEntity : bookings.values()) {
            result.add(transformToGeneralBooking(hotelBookingEntity));
        }
        return result;
    }


    private Booking transformToGeneralBooking(HotelBookingEntity hotelBookingEntity) {
        List<Booking> subBookings = new ArrayList<>();
        for (String subBookingId : hotelBookingEntity.getSubBookingId()) {
            Booking subBooking;
            subBooking = queryGateway.query(new CarBookingSummaryQuery(subBookingId),
                    ResponseTypes.instanceOf(Booking.class))
                    .join();
            if (subBooking == null) {
                subBooking = queryGateway.query(new VanBookingSummaryQuery(subBookingId),
                        ResponseTypes.instanceOf(Booking.class))
                        .join();
            }
            if (subBooking == null) {
                subBooking = queryGateway.query(new TicketBookingSummaryQuery(subBookingId),
                        ResponseTypes.instanceOf(Booking.class))
                        .join();
            }
            subBookings.add(subBooking);
        }
        return new Booking(hotelBookingEntity.getId(),
                hotelBookingEntity.getName(),
                hotelBookingEntity.getStatus(),
                hotelBookingEntity.getType(),
                subBookings);
    }
}
