package com.my.demo.service;

import com.my.demo.client.PaymentServiceClient;
import com.my.demo.dto.TicketInfoDto;
import com.my.demo.entity.PaymentStatus;
import com.my.demo.entity.Route;
import com.my.demo.entity.Ticket;
import com.my.demo.exception.RouteNotFoundException;
import com.my.demo.exception.TicketNotFoundException;
import com.my.demo.repository.RouteRepository;
import com.my.demo.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TicketInfoService {

    private final RouteRepository routeRepository;
    private final TicketRepository ticketRepository;
    private final PaymentServiceClient paymentServiceClient;

    public TicketInfoDto getTicketInfo(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new TicketNotFoundException("Ticket not found!"));
        Route route = routeRepository.findById(ticket.getRouteId()).orElseThrow(() -> new RouteNotFoundException("Route not found!"));
        Long paymentId = ticket.getPaymentId();
        PaymentStatus paymentStatus = paymentServiceClient.getPaymentStatusById(paymentId);
        return TicketInfoDto.builder()
                .departureCity(route.getDepartureCity())
                .arrivalCity(route.getArrivalCity())
                .departureDate(route.getDepartureDate())
                .departureTime(route.getDepartureTime())
                .cost(route.getCost())
                .status(paymentStatus)
                .build();
    }
}
