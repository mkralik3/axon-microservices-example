package org.mkralik.learning.axon.microservices.van;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.mkralik.learning.axon.microservices.api.Booking;
import org.mkralik.learning.axon.microservices.api.van.query.AllVanBookingSummaryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Slf4j
@Service
@Path("/van")
public class VanEndpoints {

    @Autowired
    private QueryGateway queryGateway;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Booking> getAllVan() {
        return queryGateway.query(new AllVanBookingSummaryQuery(), ResponseTypes.multipleInstancesOf(Booking.class)).join();
    }

}