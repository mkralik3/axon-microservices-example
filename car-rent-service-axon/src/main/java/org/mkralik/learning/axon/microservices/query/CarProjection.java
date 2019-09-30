package org.mkralik.learning.axon.microservices.query;

import org.mkralik.learning.axon.microservices.api.car.query.AllCarBookingSummaryQuery;
import org.mkralik.learning.axon.microservices.api.car.query.CarBookingSummaryQuery;
import org.mkralik.learning.axon.microservices.model.Booking;
import org.mkralik.learning.axon.microservices.api.car.event.ChangedCarStateEvent;
import org.mkralik.learning.axon.microservices.api.car.event.CreatedCarEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
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
public class CarProjection {

    private final EntityManager em;

    @EventHandler
    public void on(CreatedCarEvent evt){
        log.debug("projecting CreatedHotelEvent {}", evt);
        em.persist(new Booking(evt.getId(), evt.getName(), evt.getType(), evt.getStatus(), null));
        em.flush();
    }

    @EventHandler
    public void on(ChangedCarStateEvent evt){
        log.debug("projecting ChangedHotelStateEvent {}", evt);
        em.find(Booking.class, evt.getId()).setStatus(evt.getStatus());
        em.flush();
    }

    @QueryHandler
    public Booking handle(CarBookingSummaryQuery qry){
        return em.find(Booking.class, qry.getId());
    }

    @QueryHandler
    public List<Booking> handle(AllCarBookingSummaryQuery qry){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Booking> cq = cb.createQuery(Booking.class);
        Root<Booking> rootEntry = cq.from(Booking.class);
        CriteriaQuery<Booking> all = cq.select(rootEntry);
        TypedQuery<Booking> allQuery = em.createQuery(all);
        return allQuery.getResultList();
    }
}
