package com.example.loyalProgram.MerchantModule.DTOs;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoyalProgramDTO {
    private Integer id;
    private String name;
    private Integer priority;
    private BigDecimal discountPercentage;
}
