package com.example.loyalProgram.merchantModule.DTOs;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class TierDTO {
    private Integer id;
    @NotBlank(message = "Name cannot be empty!")
    @Size(min = 4, max = 50, message = "Name must be between 4 and 50 characters!")
    private String name;
    @NotNull(message = "You should provide tier amount")
    private BigDecimal tierAmount;
    @Valid
    private List<LoyalProgramDTO> loyalPrograms;
}
