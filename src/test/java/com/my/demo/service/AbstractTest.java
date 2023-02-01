package com.my.demo.service;

import com.my.demo.dto.ClientDto;
import com.my.demo.dto.TicketInfoDto;
import com.my.demo.entity.PaymentStatus;
import com.my.demo.entity.Route;
import com.my.demo.entity.Ticket;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;

@ExtendWith(MockitoExtension.class)
public class AbstractTest {

    protected Route createRoute() {
        Route route = new Route();
        route.setId(1L);
        route.setDepartureCity("departureCity");
        route.setArrivalCity("arrivalCity");
        route.setCost(120d);
        route.setAvailableSeats(20);
        route.setDepartureDate(LocalDate.of(2023, 1, 18));
        route.setDepartureTime(LocalTime.of(10, 0));
        return route;
    }

    protected ClientDto createClientDto() {
        ClientDto dto = new ClientDto();
        dto.setFirstName("firstNane");
        dto.setLastName("lastName");
        dto.setPatronymic("patronymic");
        dto.setAmount(120d);
        dto.setRouteId(1L);
        return dto;
    }

    protected Ticket createTicket() {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setFirstName("firstName");
        ticket.setLastName("lastName");
        ticket.setPatronymic("patronymic");
        ticket.setRouteId(1L);
        ticket.setPaymentId(1L);
        return ticket;
    }
    protected Ticket createTicketToSave(ClientDto dto, Long paymentId, Route route) {
        return Ticket.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .patronymic(dto.getPatronymic())
                .paymentId(paymentId)
                .routeId(route.getId())
                .build();
    }

    protected TicketInfoDto createTicketInfoDto() {
        return TicketInfoDto.builder()
                .status(PaymentStatus.DONE)
                .cost(120d)
                .departureCity("departureCity")
                .arrivalCity("arrivalCity")
                .departureDate(LocalDate.of(2023, 1, 18))
                .departureTime(LocalTime.of(10, 0))
                .build();
    }
}
