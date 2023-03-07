package com.example.loyalProgram.loyalPrograms;


import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator {
    public static BigDecimal calculatePercentage(BigDecimal price, BigDecimal discountPercentage) {
        return (price.multiply(discountPercentage).divide(BigDecimal.valueOf(100),
                RoundingMode.FLOOR).setScale(2, RoundingMode.FLOOR));
    }
}
