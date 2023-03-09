package com.example.loyalProgram.exceptionHandlingAndValidation.notFoundExceptions;

public class MerchantNotFoundException extends RuntimeException {
    public MerchantNotFoundException() {
        super("Merchant with this id is not found!");
    }
}
