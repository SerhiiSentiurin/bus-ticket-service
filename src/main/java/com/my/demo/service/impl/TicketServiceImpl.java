package com.my.demo.service.impl;

import com.my.demo.dao.RouteDao;
import com.my.demo.dao.TicketDao;
import com.my.demo.dto.ClientDto;
import com.my.demo.dto.TicketInfoDto;
import com.my.demo.entity.PaymentStatus;
import com.my.demo.entity.Route;
import com.my.demo.entity.Ticket;
import com.my.demo.exception.MoneyException;
import com.my.demo.exception.NoAvailableSeatsException;
import com.my.demo.exception.RouteNotFoundException;
import com.my.demo.exception.TicketNotFoundException;
import com.my.demo.mapper.MapperClientDtoToTicket;
import com.my.demo.mapper.MapperToTicketInfoDto;
import com.my.demo.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final RouteDao routeDao;
    private final TicketDao ticketDao;
    private final MapperClientDtoToTicket mapperToTicket;
    private final MapperToTicketInfoDto mapperToTicketInfoDto;
    private final RestTemplate restTemplate;
    private static final String CREATE_PAYMENT_POST_URL = "http://localhost:8080/payments";
    private static final String FIND_PAYMENT_STATUS_GET_URL = "http://localhost:8080/payments?id=";

    @Override
    @Transactional
    public Long buyTicket(ClientDto dto, Long routeId) {
        Route route = checkAndFindRoute(dto, routeId);
        route.setAvailableSeats(route.getAvailableSeats() - 1);
        routeDao.save(route);
//        dto.setAmount(route.getCost());
        Long paymentId = restTemplate.postForObject(CREATE_PAYMENT_POST_URL, dto, Long.class);
        Ticket ticket = mapperToTicket.map(dto, route.getId(), paymentId);
        return ticketDao.save(ticket).getId();
    }

    // Transactional reading
    @Override
    @Transactional(readOnly = true)
    public TicketInfoDto getTicketInfo(Long ticketId) {
        Ticket ticket = ticketDao.findById(ticketId).orElseThrow(() -> new TicketNotFoundException("Ticket not found!"));
        Route route = routeDao.findById(ticket.getRouteId()).orElseThrow(() -> new RouteNotFoundException("Route not found!"));
        Long id = ticket.getPaymentId();
        PaymentStatus paymentStatus = restTemplate.getForObject(FIND_PAYMENT_STATUS_GET_URL + id, PaymentStatus.class);
        return mapperToTicketInfoDto.map(route, paymentStatus);
    }

    private Route checkAndFindRoute(ClientDto dto, Long routeId) {
        Route route = routeDao.findById(routeId).orElseThrow(() -> new RouteNotFoundException("Route not found!"));
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
