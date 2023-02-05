package com.my.demo.service;

import com.my.demo.client.PaymentServiceClient;
import com.my.demo.dto.TicketBookingDto;
import com.my.demo.exception.NotEnoughFoundsException;
import com.my.demo.repository.RouteRepository;
import com.my.demo.repository.TicketRepository;
import com.my.demo.entity.Route;
import com.my.demo.entity.Ticket;
import com.my.demo.exception.NoAvailableSeatsException;
import com.my.demo.exception.RouteNotFoundException;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketBookingServiceTest {
    @Mock
    RouteRepository routeRepository;
    @Mock
    TicketRepository ticketRepository;
    @Mock
    PaymentServiceClient paymentServiceClient;

    @InjectMocks
    TicketBookingService ticketBookingService;

    @Test
    public void buyTicketTest() {
        Route expectedRoute = createRoute();
        TicketBookingDto expectedDto = createTicketBookingDto();
        Long expectedPaymentId = 1L;

        Ticket ticketToSave = createTicketToSave(expectedDto, expectedPaymentId, expectedRoute);
        Ticket expectedTicket = createTicket();

        when(routeRepository.findById(expectedDto.getRouteId())).thenReturn(Optional.of(expectedRoute));
        when(paymentServiceClient.createPayment(expectedDto)).thenReturn(expectedPaymentId);
        when(ticketRepository.save(ticketToSave)).thenReturn(expectedTicket);

        Long resultPaymentId = ticketBookingService.buyTicket(expectedDto);
        assertEquals(expectedPaymentId, resultPaymentId);
        verify(routeRepository).save(expectedRoute);
    }

    @Test
    public void buyTicketWhenRouteNotExistShouldThrowRouteNotFoundException() {
        TicketBookingDto expectedDto = createTicketBookingDto();
        when(routeRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RouteNotFoundException.class, () -> ticketBookingService.buyTicket(expectedDto));
    }

    @Test
    public void buyTicketWhenNoAvailableSeatsShouldThrowNoAvailableSeatsException() {
        Route expectedRoute = createRoute();
        TicketBookingDto clientDto = createTicketBookingDto();
        when(routeRepository.findById(expectedRoute.getId())).thenReturn(Optional.of(expectedRoute));
        expectedRoute.setAvailableSeats(0);
        assertThrows(NoAvailableSeatsException.class, () -> ticketBookingService.buyTicket(clientDto));
    }

    @Test
    public void buyTicketWhenTooMuchMoneyWereSendShouldThrowMoneyException() {
        Route expectedRoute = createRoute();
        TicketBookingDto clientDto = createTicketBookingDto();
        expectedRoute.setCost(100d);
        clientDto.setAmount(1000d);

        when(routeRepository.findById(expectedRoute.getId())).thenReturn(Optional.of(expectedRoute));
        assertThrows(NotEnoughFoundsException.class, () -> ticketBookingService.buyTicket(clientDto));
    }

    @Test
    public void buyTicketWhenNotEnoughMoneyWereSendShouldThrowMoneyException() {
        Route expectedRoute = createRoute();
        TicketBookingDto clientDto = createTicketBookingDto();
        expectedRoute.setCost(100d);
        clientDto.setAmount(0d);

        when(routeRepository.findById(expectedRoute.getId())).thenReturn(Optional.of(expectedRoute));
        assertThrows(NotEnoughFoundsException.class, () -> ticketBookingService.buyTicket(clientDto));
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

    private TicketBookingDto createTicketBookingDto() {
        return TicketBookingDto.builder()
                .firstName("firstNane")
                .lastName("lastName")
                .patronymic("patronymic")
                .amount(120d)
                .routeId(1L)
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

    private Ticket createTicketToSave(TicketBookingDto dto, Long paymentId, Route route) {
        return Ticket.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .patronymic(dto.getPatronymic())
                .paymentId(paymentId)
                .routeId(route.getId())
                .build();
    }
}
