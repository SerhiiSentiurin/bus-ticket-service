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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketInfoServiceTest {
    @Mock
    RouteRepository routeRepository;
    @Mock
    TicketRepository ticketRepository;
    @Mock
    PaymentServiceClient paymentServiceClient;

    @InjectMocks
    TicketInfoService ticketInfoService;

    @Test
    public void getTicketInfoTest() {
        Ticket expectedTicket = createTicket();
        Route expectedRoute = createRoute();
        TicketInfoDto expectedDto = createTicketInfoDto();
        PaymentStatus status = PaymentStatus.DONE;

        when(ticketRepository.findById(expectedTicket.getId())).thenReturn(Optional.of(expectedTicket));
        when(routeRepository.findById(expectedRoute.getId())).thenReturn(Optional.of(expectedRoute));
        when(paymentServiceClient.getPaymentStatusById(expectedTicket.getPaymentId())).thenReturn(status);

        TicketInfoDto resultDto = ticketInfoService.getTicketInfo(expectedTicket.getId());

        assertEquals(expectedDto, resultDto);
    }

    @Test
    public void getTicketInfoWhenTicketNotExistShouldThrowTicketNotFoundException() {
        Long ticketId = 10L;
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.empty());
        assertThrows(TicketNotFoundException.class, () -> ticketInfoService.getTicketInfo(ticketId));
    }

    @Test
    public void getTicketInfoWhenRouteNotExistShouldThrowRouteNotFoundException() {
        Ticket expectedTicket = createTicket();
        when(ticketRepository.findById(expectedTicket.getId())).thenReturn(Optional.of(expectedTicket));
        when(routeRepository.findById(expectedTicket.getRouteId())).thenReturn(Optional.empty());
        assertThrows(RouteNotFoundException.class, () -> ticketInfoService.getTicketInfo(expectedTicket.getId()));
    }

    private Route createRoute() {
        return Route.builder()
                .id(1L)
                .departureCity("departureCity")
                .arrivalCity("arrivalCity")
                .cost(120d)
                .availableSeats(20)
                .departureDate(LocalDate.of(2023, 1, 18))
                .departureTime(LocalTime.of(10, 0))
                .build();
    }
    private Ticket createTicket() {
        return Ticket.builder()
                .id(1L)
                .firstName("firstNane")
                .lastName("lastName")
                .patronymic("patronymic")
                .routeId(1L)
                .paymentId(1L)
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
