package com.my.demo.exception;

public class TicketNotFoundException extends AppException{
    public TicketNotFoundException(String message){
        super(message);
    }
}
