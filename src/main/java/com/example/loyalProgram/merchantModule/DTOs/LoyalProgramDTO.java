package com.example.loyalProgram.merchantModule.DTOs;

import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class LoyalProgramDTO {
    private Integer id;
    private String type;
    private LocalDate expirationDate;
    private Integer priority;
    private BigDecimal scale;
    private BigDecimal discountPercentage;
}
