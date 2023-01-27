package com.my.demo.dto;

import com.my.demo.entity.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder(toBuilder = true)
public class TicketInfoDto {
    private String departureCity;
    private String arrivalCity;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private Double cost;
    private PaymentStatus status;
}
