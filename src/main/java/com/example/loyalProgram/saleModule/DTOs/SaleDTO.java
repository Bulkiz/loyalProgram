package com.example.loyalProgram.saleModule.DTOs;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    private Integer clientId;
    private Integer merchantId;
    private BigDecimal price;
    private BigDecimal usedPoints;
}
