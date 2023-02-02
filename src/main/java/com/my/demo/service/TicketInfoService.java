package com.my.demo.service;

import com.my.demo.communicator.RestCommunicator;
import com.my.demo.dto.TicketInfoDto;
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
    private final RestCommunicator restCommunicator;

    public TicketInfoDto getTicketInfo(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new TicketNotFoundException("Ticket not found!"));
        Route route = routeRepository.findById(ticket.getRouteId()).orElseThrow(() -> new RouteNotFoundException("Route not found!"));
        Long paymentId = ticket.getPaymentId();
        String paymentStatus = restCommunicator.getPaymentStatusById(paymentId);
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
