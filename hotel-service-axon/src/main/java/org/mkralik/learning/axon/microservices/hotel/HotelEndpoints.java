package org.mkralik.learning.axon.microservices.hotel;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.mkralik.learning.axon.microservices.api.Booking;
import org.mkralik.learning.axon.microservices.api.car.command.CreateCarCmd;
import org.mkralik.learning.axon.microservices.api.car.query.CarBookingSummaryQuery;
import org.mkralik.learning.axon.microservices.api.hotel.command.CompensateHotelCmd;
import org.mkralik.learning.axon.microservices.api.hotel.command.CompleteHotelCmd;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.mkralik.learning.axon.microservices.api.hotel.command.CreateHotelCmd;
import org.mkralik.learning.axon.microservices.api.hotel.query.AllHotelBookingSummaryQuery;
import org.mkralik.learning.axon.microservices.api.hotel.query.HotelBookingSummaryQuery;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Collections;

import static org.eclipse.microprofile.lra.annotation.ws.rs.LRA.LRA_HTTP_CONTEXT_HEADER;

@Service
@Path("/hotel")
@LRA(LRA.Type.SUPPORTS)
@Slf4j
public class HotelEndpoints {
    @Inject
    private CommandGateway cmdGateway;

    @Inject
    private QueryGateway queryGateway;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @LRA(value = LRA.Type.REQUIRED, end = false)
    public Booking bookRoom(@HeaderParam(LRA_HTTP_CONTEXT_HEADER) String lraId,
                            @QueryParam("hotelName") @DefaultValue("Default") String hotelName) throws InterruptedException {
        //two aggregates cannot the same ID even though they are a different type
//        String carId = lraId + "CAR";
        String carId = lraId.split("/")[4]; // use lra ID as an car ID
        cmdGateway.sendAndWait(new CreateCarCmd(carId, hotelName, "Car"));
        Thread.sleep(500);
        Booking car = queryGateway.query(new CarBookingSummaryQuery(carId),
                ResponseTypes.instanceOf(Booking.class))
                .join();

        cmdGateway.sendAndWait(new CreateHotelCmd(lraId, hotelName + "Car", "Hotel", Collections.singletonList(car.getId())));
        Thread.sleep(500);
        return getBookingFromQueryBus(lraId);
    }

    @PUT
    @Path("/complete")
    @Produces(MediaType.APPLICATION_JSON)
    @Complete
    public Response completeWork(@HeaderParam(LRA_HTTP_CONTEXT_HEADER) String lraId) throws NotFoundException, InterruptedException, JsonProcessingException {
        log.info("Complete work in Axon hotel service");
        cmdGateway.sendAndWait(new CompleteHotelCmd(lraId));
        Thread.sleep(500);
        return Response.ok(getBookingFromQueryBus(lraId).toJson()).build();
    }

    @PUT
    @Path("/compensate")
    @Produces(MediaType.APPLICATION_JSON)
    @Compensate
    public Response compensateWork(@HeaderParam(LRA_HTTP_CONTEXT_HEADER) String lraId) throws NotFoundException, InterruptedException, JsonProcessingException {
        log.info("Compensate work in Axon hotel service");
        cmdGateway.sendAndWait(new CompensateHotelCmd(lraId));
        Thread.sleep(500);
        return Response.ok(getBookingFromQueryBus(lraId).toJson()).build();
    }

    @GET
    @Path("{bookingId}")
    @Produces(MediaType.APPLICATION_JSON)
    @LRA(LRA.Type.NOT_SUPPORTED)
    public Booking getBooking(@PathParam("bookingId") String bookingId) {
        return getBookingFromQueryBus(bookingId);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Booking> getAll() {
        return queryGateway.query(new AllHotelBookingSummaryQuery(), ResponseTypes.multipleInstancesOf(Booking.class)).join();
    }

    private Booking getBookingFromQueryBus(String lraId) {
        Booking join = queryGateway.query(new HotelBookingSummaryQuery(lraId),
                ResponseTypes.instanceOf(Booking.class))
                .join();
        log.debug("returned class is {}", join);
        return join;
    }
}
