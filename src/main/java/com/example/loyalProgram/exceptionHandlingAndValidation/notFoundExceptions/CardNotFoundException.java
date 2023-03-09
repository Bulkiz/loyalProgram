package com.example.loyalProgram.exceptionHandlingAndValidation.notFoundExceptions;

public class CardNotFoundException extends RuntimeException{

    public CardNotFoundException() {
        super("Card with this id is not found!");
    }
}
