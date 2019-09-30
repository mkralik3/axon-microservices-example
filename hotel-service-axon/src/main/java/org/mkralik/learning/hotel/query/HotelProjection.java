package org.mkralik.learning.hotel.query;

import org.mkralik.learning.axon.microservices.api.hotel.event.ChangedHotelStateEvent;
import org.mkralik.learning.axon.microservices.api.hotel.event.CreatedHotelEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.mkralik.learning.axon.microservices.api.hotel.query.AllHotelBookingSummaryQuery;
import org.mkralik.learning.axon.microservices.api.hotel.query.HotelBookingSummaryQuery;
import org.mkralik.learning.hotel.model.Booking;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class HotelProjection {

    private final EntityManager em;

    @EventHandler
    public void on(CreatedHotelEvent evt){
        log.debug("projecting CreatedHotelEvent {}", evt);
        em.persist(new Booking(evt.getId().toString(), evt.getName(), evt.getType(), evt.getStatus(), null));
        em.flush();
    }

    @EventHandler
    public void on(ChangedHotelStateEvent evt){
        log.debug("projecting ChangedHotelStateEvent {}", evt);
        em.find(Booking.class, evt.getId()).setStatus(evt.getStatus());
        em.flush();
    }

    @QueryHandler
    public Booking handle(HotelBookingSummaryQuery qry){
        return em.find(Booking.class, qry.getId());
    }

    @QueryHandler
    public List<Booking> handle(AllHotelBookingSummaryQuery qry){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Booking> cq = cb.createQuery(Booking.class);
        Root<Booking> rootEntry = cq.from(Booking.class);
        CriteriaQuery<Booking> all = cq.select(rootEntry);
        TypedQuery<Booking> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }
}
