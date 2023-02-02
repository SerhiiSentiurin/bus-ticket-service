package com.my.demo.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("tickets")
@Data
@Builder(toBuilder = true)
public class Ticket {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Long routeId;
    private Long paymentId;

}
