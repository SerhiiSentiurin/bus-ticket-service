package com.my.demo.scheduler;

import com.my.demo.client.PaymentServiceClient;
import com.my.demo.entity.Payment;
import com.my.demo.entity.PaymentStatus;
import com.my.demo.entity.Route;
import com.my.demo.entity.Ticket;
import com.my.demo.exception.RouteNotFoundException;
import com.my.demo.exception.TicketNotFoundException;
import com.my.demo.repository.RouteRepository;
import com.my.demo.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Component
@RequiredArgsConstructor
public class TicketScheduler {

    private final TicketRepository ticketRepository;
    private final RouteRepository routeRepository;
    private final PaymentServiceClient paymentServiceClient;


    @Scheduled(fixedRate = 3000)
    @Transactional
    public void scheduledChangeTicketPaymentStatus() {
        List<Payment> paymentsWithStatusNew = paymentServiceClient.getPaymentsByStatus(PaymentStatus.NEW);
        if (paymentsWithStatusNew.size() > 0) {
            List<Payment> paymentsWithRandomStatus = getPaymentsWithRandomStatus(paymentsWithStatusNew);
            for (Payment payment : paymentsWithRandomStatus) {
                if (payment.getStatus().equals(PaymentStatus.FAILED)) {
                    returnFailedTicket(payment);
                    log.info("scheduler - returned one ticket with payment id = " + payment.getId() + " cause: payment status is FAILED");
                }
            }
            paymentServiceClient.updateStatuses(paymentsWithRandomStatus);
        }
    }

    private List<Payment> getPaymentsWithRandomStatus(List<Payment> paymentsWithStatusNew) {
        return paymentsWithStatusNew.stream()
                .map(payment -> payment.toBuilder()
                        .id(payment.getId())
                        .firstName(payment.getFirstName())
                        .lastName(payment.getLastName())
                        .patronymic(payment.getPatronymic())
                        .amount(payment.getAmount())
                        .status(paymentServiceClient.getPaymentStatusById(payment.getId()))
                        .build())
                .collect(Collectors.toList());
    }

    private void returnFailedTicket(Payment payment) {
        Ticket ticketByPaymentId = ticketRepository.findByPaymentId(payment.getId()).orElseThrow(() -> new TicketNotFoundException("Ticket with paymentId " + payment.getId() + " not found"));
        Route routeByPaymentId = routeRepository.findById(ticketByPaymentId.getRouteId()).orElseThrow(() -> new RouteNotFoundException("Route with " + ticketByPaymentId.getRouteId() + " not found"));
        routeByPaymentId.setAvailableSeats(routeByPaymentId.getAvailableSeats() + 1);
        routeRepository.save(routeByPaymentId);
    }
}
