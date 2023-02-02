package com.my.demo.repository;

import com.my.demo.entity.Ticket;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface TicketRepository extends CrudRepository<Ticket, Long> {

    @Modifying
    @Query("update routes join tickets on routes.id = tickets.route_id set available_seats = available_seats + 1 where payment_id = :paymentId")
    void updateAvailableSeatsWithReturnedTicketForUpdate (@Param("paymentId") Long paymentId);
}
