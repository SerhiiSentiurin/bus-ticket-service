package com.my.demo.dto;

import lombok.Data;

@Data
public class TicketReturnDto {
    private Long id;
    private Long routeId;
    private Long paymentId;
}
