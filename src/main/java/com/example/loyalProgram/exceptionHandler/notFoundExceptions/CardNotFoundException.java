package com.example.loyalProgram.exceptionHandler.notFoundExceptions;

public class CardNotFoundException extends RuntimeException{

    public CardNotFoundException() {
        super("Card with this id is not found!");
    }
}
