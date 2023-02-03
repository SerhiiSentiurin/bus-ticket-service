package com.my.demo.dto;

import lombok.Data;

@Data
public class TicketPurchaseDto {
    private String firstName;
    private String lastName;
    private String patronymic;
    private Double amount;
    private Long routeId;
}
