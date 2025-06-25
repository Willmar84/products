package com.capitole.zara.products.domain.exceptions;

public class RateNotFoundException extends RuntimeException{
    public RateNotFoundException(String message){
        super(message);
    }
}