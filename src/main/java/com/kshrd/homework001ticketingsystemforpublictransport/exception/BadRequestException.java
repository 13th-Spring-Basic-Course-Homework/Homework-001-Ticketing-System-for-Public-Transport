package com.kshrd.homework001ticketingsystemforpublictransport.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String message) {
        super(message);
    }
}
