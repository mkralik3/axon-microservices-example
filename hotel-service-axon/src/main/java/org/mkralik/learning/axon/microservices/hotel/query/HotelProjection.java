package org.mkralik.learning.axon.microservices.hotel.query;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.mkralik.learning.axon.microservices.api.Booking;
import org.mkralik.learning.axon.microservices.api.car.event.ChangedCarStateEvent;
import org.mkralik.learning.axon.microservices.api.car.query.CarBookingSummaryQuery;
import org.mkralik.learning.axon.microservices.api.hotel.event.ChangedHotelStateEvent;
import org.mkralik.learning.axon.microservices.api.hotel.event.CreatedHotelEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.mkralik.learning.axon.microservices.api.hotel.query.AllHotelBookingSummaryQuery;
import org.mkralik.learning.axon.microservices.api.hotel.query.HotelBookingSummaryQuery;
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
        log.debug("projecting CreatedHotelEvent {}", evt);
        //get cars from car service
        List<Booking> cars = new ArrayList<>();
        for (String carId : evt.getCarsId()) {
            Booking car = queryGateway.query(new CarBookingSummaryQuery(carId),
                    ResponseTypes.instanceOf(Booking.class))
                    .join();
            cars.add(car);
        }
        bookings.put(evt.getId(), new Booking(evt.getId(), evt.getName(), evt.getStatus(), evt.getType(), cars));
    }

    @EventHandler
    public void on(ChangedCarStateEvent evt){
        log.debug("projecting change state in the Hotel service {}", evt);
        //find all hotel where is the particular car
        String searchingCarId = evt.getId();

        List<Booking> hotelsForUpdate = bookings.values().stream().filter(hotel -> hotel.getDetails().stream()
                .anyMatch(car -> car.getId().equals(searchingCarId)))
                .collect(Collectors.toList());

        for (Booking hotel : hotelsForUpdate) {
            hotel.getDetails().stream()
                    .filter(car -> car.getId().equals(searchingCarId))
                    .findAny()
                    .ifPresent(car -> car.setStatus(evt.getStatus()));
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
