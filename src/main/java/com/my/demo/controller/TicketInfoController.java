package com.my.demo.controller;

import com.my.demo.dto.TicketInfoDto;
import com.my.demo.service.TicketInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketInfoController {

    private final TicketInfoService ticketInfoService;

    @GetMapping("/info")
    public TicketInfoDto getTicketInfo(@RequestParam Long ticketId) {
        return ticketInfoService.getTicketInfo(ticketId);
    }
}
