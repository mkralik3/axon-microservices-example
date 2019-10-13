package org.mkralik.learning.axon.microservices.api.vehicle

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
data class CreateCarCmd @JvmOverloads constructor(@TargetAggregateIdentifier val id: String,
                                                  val name: String,
                                                  val status: Booking.BookingStatus = Booking.BookingStatus.PROVISIONAL,
                                                  val type: String)
@JoinLRA
data class CreateVanCmd @JvmOverloads constructor(@TargetAggregateIdentifier val id: String,
                                                  val name: String,
                                                  val status: Booking.BookingStatus = Booking.BookingStatus.PROVISIONAL,
                                                  val type: String)

/**
 * events
 */
data class ChangedVanStateEvent(val id: String, val status: Booking.BookingStatus)
data class ChangedCarStateEvent(val id: String, val status: Booking.BookingStatus)
data class CreatedCarEvent(val id: String,
                           val name: String,
                           val status: Booking.BookingStatus = Booking.BookingStatus.PROVISIONAL,
                           val type: String)
data class CreatedVanEvent(val id: String,
                           val name: String,
                           val status: Booking.BookingStatus = Booking.BookingStatus.PROVISIONAL,
                           val type: String)

/**
 * queries
 */
class AllVanBookingSummaryQuery()
class AllCarBookingSummaryQuery()
data class CarBookingSummaryQuery(val id: String)
data class VanBookingSummaryQuery(val id: String)
