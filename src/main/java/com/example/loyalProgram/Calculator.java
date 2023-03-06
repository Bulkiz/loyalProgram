package com.example.loyalProgram;


import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator {
    public static BigDecimal calculatePercentage(BigDecimal price, BigDecimal discountPercentage) {
        return price.multiply(discountPercentage).divide(BigDecimal.valueOf(100), RoundingMode.FLOOR);
    }
}
