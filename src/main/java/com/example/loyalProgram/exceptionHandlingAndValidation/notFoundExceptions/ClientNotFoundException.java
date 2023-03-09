package com.example.loyalProgram.exceptionHandlingAndValidation.notFoundExceptions;

public class ClientNotFoundException extends RuntimeException{

    public ClientNotFoundException() {
        super("Client with this id is not found!");
    }
}
