package com.my.demo.dto;

import lombok.Data;

@Data
public class ClientDto {
    private String firstName;
    private String lastName;
    private String patronymic;
    private Double amount;
}