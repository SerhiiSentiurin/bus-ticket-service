package com.my.demo.repository;

import com.my.demo.entity.Ticket;
import org.springframework.data.repository.CrudRepository;


public interface TicketRepository extends CrudRepository<Ticket, Long> {

//    @Modifying
//    @Query("update routes join tickets on routes.id = tickets.route_id set available_seats = available_seats + 1 where payment_id = :paymentId")
//    void updateAvailableSeatsWithReturnedTicketForUpdate (@Param("paymentId") Long paymentId);

    Ticket findByPaymentId(Long paymentId);
}
