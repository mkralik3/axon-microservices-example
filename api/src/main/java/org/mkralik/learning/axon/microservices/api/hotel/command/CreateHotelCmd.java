package org.mkralik.learning.axon.microservices.api.hotel.command;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.mkralik.learning.axon.microservices.api.Booking;

@Value
public class CreateHotelCmd {

    @TargetAggregateIdentifier
    private String id;
    private String name;
    private Booking.BookingStatus status = Booking.BookingStatus.PROVISIONAL;
    private String type;
    private Booking[] details;
}
