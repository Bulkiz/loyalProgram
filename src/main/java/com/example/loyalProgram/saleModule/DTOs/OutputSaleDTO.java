package com.example.loyalProgram.saleModule.DTOs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class OutputSaleDTO {
    private Integer id;
    private BigDecimal originalPrice;
    private BigDecimal discountedPrice;
    private BigDecimal summaryPrice;
    private BigDecimal usedPoints;
    private BigDecimal cardBalance;
}
