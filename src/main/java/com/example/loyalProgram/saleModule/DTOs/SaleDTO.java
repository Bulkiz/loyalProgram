package com.example.loyalProgram.saleModule.DTOs;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
public class SaleDTO {
    private Integer clientId;
    private Integer merchantId;
    private Integer cardId;
    private BigDecimal price;
    private BigDecimal usedPoints;
}
