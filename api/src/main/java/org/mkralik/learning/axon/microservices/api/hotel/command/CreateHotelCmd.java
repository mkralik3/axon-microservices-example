package org.mkralik.learning.axon.microservices.api.hotel.command;

import org.mkralik.learning.axon.microservices.model.Booking;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CreateHotelCmd {

    @TargetAggregateIdentifier
    private String id;
    private String name;
    private Booking.BookingStatus status = Booking.BookingStatus.PROVISIONAL;
    private String type;
    private Booking[] details = null;
}
