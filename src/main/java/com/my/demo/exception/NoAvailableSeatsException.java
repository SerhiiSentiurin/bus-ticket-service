package com.my.demo.exception;

public class NoAvailableSeatsException extends RuntimeException{
    public NoAvailableSeatsException(String message){
        super(message);
    }
}
