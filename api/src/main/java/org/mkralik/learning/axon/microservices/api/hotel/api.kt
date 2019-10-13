package org.mkralik.learning.axon.microservices.api.hotel

import org.axonframework.modelling.command.TargetAggregateIdentifier
import org.mkralik.learning.axon.microservices.api.Booking

/**
 * Annotation @JvmOverloads is needed because kotlin cannot create a constructor for Java with all combinations of parameters
 */

/**
 * commands
 */
data class CompensateHotelCmd @JvmOverloads constructor(@TargetAggregateIdentifier val id: String, var status: Booking.BookingStatus = Booking.BookingStatus.CANCELLED)
data class CompleteHotelCmd @JvmOverloads constructor(@TargetAggregateIdentifier val id: String, var status: Booking.BookingStatus = Booking.BookingStatus.CONFIRMED)
data class CreateHotelCmd @JvmOverloads constructor(@TargetAggregateIdentifier val id: String,
                                                    val name: String,
                                                    val status: Booking.BookingStatus = Booking.BookingStatus.PROVISIONAL,
                                                    val type: String,
                                                    val subBookingsId: List<String>)

/**
 * events
 */
data class ChangedHotelStateEvent(val id: String, val status: Booking.BookingStatus)
data class CreatedHotelEvent(val id: String,
                             val name: String,
                             val status: Booking.BookingStatus = Booking.BookingStatus.PROVISIONAL,
                             val type: String,
                             val subBookingsId: List<String>)

/**
 * queries
 */
class AllHotelBookingSummaryQuery()
data class HotelBookingSummaryQuery(val id: String)
