package com.my.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("payments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    private Long id;
    private Double amount;
    private PaymentStatus status;
}
