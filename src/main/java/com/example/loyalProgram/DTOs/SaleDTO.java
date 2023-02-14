package com.example.loyalProgram.DTOs;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
public class SaleDTO {
    private Integer clientId;
    private Integer merchantId;
    private BigDecimal price;
}
