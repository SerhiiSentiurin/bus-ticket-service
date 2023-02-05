package com.my.demo.service;

import com.my.demo.client.PaymentServiceClient;
import com.my.demo.repository.RouteRepository;
import com.my.demo.repository.TicketRepository;
import com.my.demo.dto.TicketBookingDto;
import com.my.demo.entity.Route;
import com.my.demo.entity.Ticket;
import com.my.demo.exception.NotEnoughFoundsException;
import com.my.demo.exception.NoAvailableSeatsException;
import com.my.demo.exception.RouteNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class TicketBookingService {
    private final RouteRepository routeRepository;
    private final TicketRepository ticketRepository;
    private final PaymentServiceClient paymentServiceClient;

    @Transactional
    public Long buyTicket(TicketBookingDto dto) {
        Route route = checkAndFindRoute(dto);
        route.setAvailableSeats(route.getAvailableSeats() - 1);
        Long paymentId = paymentServiceClient.createPayment(dto);
        Ticket ticket = Ticket.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .patronymic(dto.getPatronymic())
                .paymentId(paymentId)
                .routeId(route.getId())
                .build();
        routeRepository.save(route);
        return ticketRepository.save(ticket).getId();
    }

    private Route checkAndFindRoute(TicketBookingDto dto) {
        Route route = routeRepository.findById(dto.getRouteId()).orElseThrow(() -> new RouteNotFoundException("Route not found!"));
        if (route.getAvailableSeats() <= 0) {
            throw new NoAvailableSeatsException("No available seats!");
        }
        if (dto.getAmount() > route.getCost()) {
            throw new NotEnoughFoundsException("Too much money, ticket cost is : " + route.getCost());
        }
        if (dto.getAmount() < route.getCost()) {
            throw new NotEnoughFoundsException("Not enough money, ticket cost is : " + route.getCost());
        }
        return route;
    }
}
