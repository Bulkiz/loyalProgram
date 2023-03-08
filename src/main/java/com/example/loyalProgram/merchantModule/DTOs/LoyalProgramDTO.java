package com.example.loyalProgram.merchantModule.DTOs;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LoyalProgramDTO {
    private Integer id;
    @NotBlank(message = "You should provide the type")
    private String type;
    private Integer priority;
    private BigDecimal scale;
    @DecimalMin(value = "5.0", message = "Discount percentage must be more than 5")
    @DecimalMax(value = "20.0", message = "Discount percentage must be less than 20")
    private BigDecimal discountPercentage;
    @DecimalMin(value = "5.0", message = "Discount percentage must be more than 5")
    @DecimalMax(value = "20.0", message = "Discount percentage must be less than 20")
    private BigDecimal addPointsPercentage;
}
