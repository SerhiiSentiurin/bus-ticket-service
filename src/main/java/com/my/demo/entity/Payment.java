package com.my.demo.entity;

import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
public class Payment {
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Double amount;
    private PaymentStatus status;
}
