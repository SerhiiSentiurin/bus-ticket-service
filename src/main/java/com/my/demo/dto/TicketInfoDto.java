package com.my.demo.dto;

import com.my.demo.entity.PaymentStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TicketInfoDto {
    private String departureCity;
    private String arrivalCity;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private Double cost;
    private PaymentStatus status;
}
