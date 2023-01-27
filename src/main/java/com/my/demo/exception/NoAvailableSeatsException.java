package com.my.demo.exception;

public class NoAvailableSeatsException extends AppException{
    public NoAvailableSeatsException(String message){
        super(message);
    }
}
