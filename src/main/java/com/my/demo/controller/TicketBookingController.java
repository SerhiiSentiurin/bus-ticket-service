package com.my.demo.controller;

import com.my.demo.dto.TicketBookingDto;
import com.my.demo.service.TicketBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketBookingController {

    private final TicketBookingService ticketBookingService;

    @PostMapping("/buy")
    public Long buyTicket(@RequestBody TicketBookingDto dto) {
        return ticketBookingService.buyTicket(dto);
    }
}
