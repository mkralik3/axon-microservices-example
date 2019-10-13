package org.mkralik.learning.axon.microservices.vehicle;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.mkralik.learning.axon.microservices.api.Booking;
import org.mkralik.learning.axon.microservices.api.vehicle.query.AllVanBookingSummaryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Service
@RequestMapping("/van")
@Slf4j
@RestController
public class VanEndpoints {

    @Autowired
    private QueryGateway queryGateway;

    @GetMapping
    public Collection<Booking> getAllVan() {
        return queryGateway.query(new AllVanBookingSummaryQuery(), ResponseTypes.multipleInstancesOf(Booking.class)).join();
    }

}