package com.my.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class TicketBookingDto {
    private String firstName;
    private String lastName;
    private String patronymic;
    private Double amount;
    private Long routeId;
}
