package com.my.demo.mapper;

import com.my.demo.dto.TicketInfoDto;
import com.my.demo.entity.PaymentStatus;
import com.my.demo.entity.Route;
import org.springframework.stereotype.Component;

@Component
public class MapperToTicketInfoDto {

    public TicketInfoDto map(Route route, PaymentStatus paymentStatus){
        TicketInfoDto ticketInfoDto = new TicketInfoDto();
        ticketInfoDto.setDepartureCity(route.getDepartureCity());
        ticketInfoDto.setArrivalCity(route.getArrivalCity());
        ticketInfoDto.setDepartureDate(route.getDepartureDate());
        ticketInfoDto.setDepartureTime(route.getDepartureTime());
        ticketInfoDto.setCost(route.getCost());
        ticketInfoDto.setStatus(paymentStatus);
        return ticketInfoDto;
    }
}
