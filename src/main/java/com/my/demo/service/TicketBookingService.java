package com.my.demo.service;

import com.my.demo.communicator.RestCommunicator;
import com.my.demo.repository.RouteRepository;
import com.my.demo.repository.TicketRepository;
import com.my.demo.dto.ClientDto;
import com.my.demo.entity.Route;
import com.my.demo.entity.Ticket;
import com.my.demo.exception.MoneyException;
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
    private final RestCommunicator restCommunicator;

    @Transactional
    public Long buyTicket(ClientDto dto) {
        Route route = checkAndFindRoute(dto);
        route.setAvailableSeats(route.getAvailableSeats() - 1);
        routeRepository.save(route);
        Long paymentId = restCommunicator.createPayment(dto);
        Ticket ticket = Ticket.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .patronymic(dto.getPatronymic())
                .paymentId(paymentId)
                .routeId(route.getId())
                .build();
        return ticketRepository.save(ticket).getId();
    }

    private Route checkAndFindRoute(ClientDto dto) {
        Route route = routeRepository.findById(dto.getRouteId()).orElseThrow(() -> new RouteNotFoundException("Route not found!"));
        if (route.getAvailableSeats() <= 0) {
            throw new NoAvailableSeatsException("No available seats!");
        }
        if (dto.getAmount() > route.getCost()) {
            throw new MoneyException("Too much money, ticket cost is : " + route.getCost());
        }
        if (dto.getAmount() < route.getCost()) {
            throw new MoneyException("Not enough money, ticket cost is : " + route.getCost());
        }
        return route;
    }
}
