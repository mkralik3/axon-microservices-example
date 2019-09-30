package org.mkralik.learning.axon.microservices.car;

import org.mkralik.learning.axon.microservices.api.Booking;
import org.mkralik.learning.axon.microservices.api.car.query.AllCarBookingSummaryQuery;
import org.mkralik.learning.axon.microservices.api.car.query.CarBookingSummaryQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Service
@Path("/car")
@Slf4j
public class CarEndpoints {

    @Autowired
    private QueryGateway queryGateway;

    @GET
    @Path("{bookingId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Booking getBooking(@PathParam("bookingId") String bookingId) {
        return getBookingFromQueryBus(bookingId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
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
