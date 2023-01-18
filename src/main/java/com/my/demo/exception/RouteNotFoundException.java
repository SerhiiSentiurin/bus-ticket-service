package com.my.demo.exception;

public class RouteNotFoundException extends RuntimeException{
    public RouteNotFoundException(String message){
        super(message);
    }
}
