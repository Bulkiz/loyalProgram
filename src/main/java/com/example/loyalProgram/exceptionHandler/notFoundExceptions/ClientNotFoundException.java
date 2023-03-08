package com.example.loyalProgram.exceptionHandler.notFoundExceptions;

public class ClientNotFoundException extends RuntimeException{

    public ClientNotFoundException() {
        super("Client with this id is not found!");
    }
}
