package org.mkralik.learning.axon.microservices.car;

import org.mkralik.learning.axon.microservices.api.Booking;
import org.mkralik.learning.axon.microservices.api.car.query.AllCarBookingSummaryQuery;
import org.mkralik.learning.axon.microservices.api.car.query.CarBookingSummaryQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Service
@RequestMapping("/car")
@Slf4j
@RestController
public class CarEndpoints {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/{bookingId}")
    public Booking getBooking(@PathVariable("bookingId") String bookingId) {
        return getBookingFromQueryBus(bookingId);
    }

    @GetMapping
    public Collection<Booking> getAll() {
        return queryGateway.query(new AllCarBookingSummaryQuery(), ResponseTypes.multipleInstancesOf(Booking.class)).join();
    }

    private Booking getBookingFromQueryBus(String lraId) {
        Booking join = queryGateway.query(new CarBookingSummaryQuery(lraId),
                ResponseTypes.instanceOf(Booking.class))
                .join();
        log.debug("returned class is {}", join);
        return join;
    }
}