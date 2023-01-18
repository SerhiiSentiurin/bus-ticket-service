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

    protected TicketInfoDto createTicketInfoDto() {
        TicketInfoDto dto = new TicketInfoDto();
        dto.setStatus(PaymentStatus.DONE);
        dto.setCost(120d);
        dto.setDepartureCity("departureCity");
        dto.setArrivalCity("arrivalCity");
        dto.setDepartureDate(LocalDate.of(2023, 1, 18));
        dto.setDepartureTime(LocalTime.of(10, 0));
        return dto;
    }
}
