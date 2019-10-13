package org.mkralik.learning.axon.microservices.api.cinema

import org.axonframework.modelling.command.TargetAggregateIdentifier
import org.mkralik.learning.axon.microservices.api.Booking
import org.mkralik.learning.lra.axon.api.annotation.JoinLRA
import org.mkralik.learning.lra.axon.api.annotation.LRAContext
import java.net.URI

/**
 * Annotation @JvmOverloads is needed because kotlin cannot create a constructor for Java with all combinations of parameters
 */

/**
 * commands
 */
@JoinLRA
data class CreateTicketCmd @JvmOverloads constructor(@TargetAggregateIdentifier val id: String,
                                                     @LRAContext val context: URI,
                                                     val name: String,
                                                     val status: Booking.BookingStatus = Booking.BookingStatus.PROVISIONAL,
                                                     val type: String)

/**
 * events
 */
data class ChangedTicketStateEvent(val id: String, val status: Booking.BookingStatus)
data class CreatedTicketEvent(val id: String,
                              val name: String,
                              val status: Booking.BookingStatus = Booking.BookingStatus.PROVISIONAL,
                              val type: String)

/**
 * queries
 */
class AllTicketBookingSummaryQuery()
data class TicketBookingSummaryQuery(val id: String)
