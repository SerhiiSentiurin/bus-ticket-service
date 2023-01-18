package com.my.demo.mapper;

import com.my.demo.dto.ClientDto;
import com.my.demo.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class MapperClientDtoToTicket {

    public Ticket map (ClientDto dto, Long routeId, Long paymentId){
        Ticket ticket = new Ticket();
        ticket.setFirstName(dto.getFirstName());
        ticket.setLastName(dto.getLastName());
        ticket.setPatronymic(dto.getPatronymic());
        ticket.setRouteId(routeId);
        ticket.setPaymentId(paymentId);
        return ticket;
    }
}
