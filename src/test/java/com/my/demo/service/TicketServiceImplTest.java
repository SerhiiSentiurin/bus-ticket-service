//package com.my.demo.service;
//
//import com.my.demo.dao.RouteDao;
//import com.my.demo.dao.TicketDao;
//import com.my.demo.dto.ClientDto;
//import com.my.demo.dto.TicketInfoDto;
//import com.my.demo.entity.PaymentStatus;
//import com.my.demo.entity.Route;
//import com.my.demo.entity.Ticket;
//import com.my.demo.exception.MoneyException;
//import com.my.demo.exception.NoAvailableSeatsException;
//import com.my.demo.exception.RouteNotFoundException;
//import com.my.demo.exception.TicketNotFoundException;
//import com.my.demo.mapper.MapperClientDtoToTicket;
//import com.my.demo.mapper.MapperToTicketInfoDto;
//import com.my.demo.service.impl.TicketServiceImpl;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//
//
//public class TicketServiceImplTest extends AbstractTest {
//    @Mock
//    RouteDao routeDao;
//    @Mock
//    TicketDao ticketDao;
//    @Mock
//    MapperClientDtoToTicket mapperToTicket;
//    @Mock
//    MapperToTicketInfoDto mapperToTicketInfoDto;
//    @Mock
//    RestTemplate restTemplate;
//
//    @InjectMocks
//    TicketServiceImpl ticketService;
//    private static final String CREATE_PAYMENT_POST_URL = "http://localhost:8080/payments";
//    private static final String FIND_PAYMENT_STATUS_GET_URL = "http://localhost:8080/payments?id=";
//
//    @Test
//    public void buyTicketTest() {
//        Route expectedRoute = createRoute();
//        ClientDto expectedDto = createClientDto();
//        Ticket expectedTicket = createTicket();
//        Long expectedPaymentId = 1L;
//
//        when(routeDao.findById(expectedRoute.getId())).thenReturn(Optional.of(expectedRoute));
//        when(restTemplate.postForObject(CREATE_PAYMENT_POST_URL, expectedDto, Long.class)).thenReturn(expectedPaymentId);
//        when(mapperToTicket.map(expectedDto, expectedRoute.getId(), expectedPaymentId)).thenReturn(expectedTicket);
//        when(ticketDao.save(expectedTicket)).thenReturn(expectedTicket);
//
//        Long resultPaymentId = ticketService.buyTicket(expectedDto);
//        assertEquals(expectedPaymentId, resultPaymentId);
//    }
//
//    @Test
//    public void buyTicketWhenRouteNotExistShouldThrowRouteNotFoundException() {
//        ClientDto expectedDto = createClientDto();
//        when(routeDao.findById(anyLong())).thenReturn(Optional.empty());
//        assertThrows(RouteNotFoundException.class, () -> ticketService.buyTicket(expectedDto));
//    }
//
//    @Test
//    public void buyTicketWhenNoAvailableSeatsShouldThrowNoAvailableSeatsException() {
//        Route expectedRoute = createRoute();
//        ClientDto clientDto = createClientDto();
//        when(routeDao.findById(expectedRoute.getId())).thenReturn(Optional.of(expectedRoute));
//        expectedRoute.setAvailableSeats(0);
//        assertThrows(NoAvailableSeatsException.class, () -> ticketService.buyTicket(clientDto));
//    }
//
//    @Test
//    public void buyTicketWhenTooMuchMoneyWereSendShouldThrowMoneyException() {
//        Route expectedRoute = createRoute();
//        ClientDto clientDto = createClientDto();
//        expectedRoute.setCost(100d);
//        clientDto.setAmount(1000d);
//
//        when(routeDao.findById(expectedRoute.getId())).thenReturn(Optional.of(expectedRoute));
//        assertThrows(MoneyException.class, () -> ticketService.buyTicket(clientDto));
//    }
//
//    @Test
//    public void buyTicketWhenNotEnoughMoneyWereSendShouldThrowMoneyException() {
//        Route expectedRoute = createRoute();
//        ClientDto clientDto = createClientDto();
//        expectedRoute.setCost(100d);
//        clientDto.setAmount(0d);
//
//        when(routeDao.findById(expectedRoute.getId())).thenReturn(Optional.of(expectedRoute));
//        assertThrows(MoneyException.class, () -> ticketService.buyTicket(clientDto));
//    }
//
//    @Test
//    public void getTicketInfoTest() {
//        Ticket expectedTicket = createTicket();
//        Route expectedRoute = createRoute();
//        TicketInfoDto expectedDto = createTicketInfoDto();
//        PaymentStatus status = PaymentStatus.DONE;
//
//        when(ticketDao.findById(expectedTicket.getId())).thenReturn(Optional.of(expectedTicket));
//        when(routeDao.findById(expectedRoute.getId())).thenReturn(Optional.of(expectedRoute));
//        when(restTemplate.getForObject(FIND_PAYMENT_STATUS_GET_URL + expectedTicket.getPaymentId(), PaymentStatus.class)).thenReturn(status);
//        when(mapperToTicketInfoDto.map(expectedRoute, status)).thenReturn(expectedDto);
//
//        TicketInfoDto resultDto = ticketService.getTicketInfo(expectedTicket.getId());
//
//        assertEquals(expectedDto, resultDto);
//    }
//
//    @Test
//    public void getTicketInfoWhenTicketNotExistShouldThrowTicketNotFoundException() {
//        Long ticketId = 10L;
//        when(ticketDao.findById(ticketId)).thenReturn(Optional.empty());
//        assertThrows(TicketNotFoundException.class, () -> ticketService.getTicketInfo(ticketId));
//    }
//
//    @Test
//    public void getTicketInfoWhenRouteNotExistShouldThrowRouteNotFoundException(){
//        Ticket expectedTicket = createTicket();
//        when(ticketDao.findById(expectedTicket.getId())).thenReturn(Optional.of(expectedTicket));
//        when(routeDao.findById(expectedTicket.getRouteId())).thenReturn(Optional.empty());
//        assertThrows(RouteNotFoundException.class, ()-> ticketService.getTicketInfo(expectedTicket.getId()));
//    }
//
//}
