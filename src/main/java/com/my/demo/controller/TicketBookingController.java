package com.my.demo.controller;

import com.my.demo.dto.TicketPurchaseDto;
import com.my.demo.service.TicketBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketBookingController {

    private final TicketBookingService ticketBookingService;

    @PostMapping("/buy")
    public Long buyTicket(@RequestBody TicketPurchaseDto dto) {
        return ticketBookingService.buyTicket(dto);
    }
}
