package com.example.loyalProgram.saleModule.DTOs;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
public class SaleDTO {
    private Integer clientId;
    private Integer merchantId;
    private Integer cardId;
    @NotNull(message = "You should provide price")
    private BigDecimal price;
    @NotNull
    private BigDecimal usedPoints;
}
