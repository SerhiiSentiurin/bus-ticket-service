package com.my.demo.repository;

import com.my.demo.entity.Ticket;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface TicketRepository extends CrudRepository<Ticket, Long> {

    Optional<Ticket> findByPaymentId(Long paymentId);
}
