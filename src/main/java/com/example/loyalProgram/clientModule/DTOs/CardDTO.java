package com.example.loyalProgram.clientModule.DTOs;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
public class CardDTO {
    private Integer id;
    private BigDecimal balance;
}
