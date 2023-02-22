package com.example.loyalProgram.merchantModule.DTOs;

import com.example.loyalProgram.enums.LoyalProgramType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
public class LoyalProgramDTO {
    private Integer id;
    private String name;
    private Integer priority;
    private BigDecimal discountPercentage;
    private LoyalProgramType type;
}
