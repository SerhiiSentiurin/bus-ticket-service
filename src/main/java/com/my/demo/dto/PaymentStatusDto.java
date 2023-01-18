package com.my.demo.dto;

import com.my.demo.entity.PaymentStatus;
import lombok.Data;

@Data
public class PaymentStatusDto {
    private Long id;
    private PaymentStatus status;
}
