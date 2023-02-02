package com.my.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalTime;

@Table("routes")
@Data
public class Route {
    @Id
    private Long id;
    private String departureCity;
    private String arrivalCity;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private Double cost;
    private Integer availableSeats;
}
