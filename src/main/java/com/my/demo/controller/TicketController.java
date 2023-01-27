package com.my.demo.controller;

import com.my.demo.dto.ClientDto;
import com.my.demo.dto.TicketInfoDto;
import com.my.demo.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/buy")
    public Long buyTicket(@RequestBody ClientDto dto) {
        return ticketService.buyTicket(dto);
    }

    @GetMapping
    public TicketInfoDto getTicketInfo (@RequestParam Long ticketId){
        return ticketService.getTicketInfo(ticketId);
    }
}
