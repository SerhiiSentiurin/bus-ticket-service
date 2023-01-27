package com.my.demo.service;

import com.my.demo.dto.ClientDto;
import com.my.demo.dto.TicketInfoDto;

public interface TicketService {
    Long buyTicket(ClientDto dto);
    TicketInfoDto getTicketInfo(Long ticketId);

}
