package com.example.loyalProgram.ClientModule.DTOs;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {
    private Integer id;
    private BigDecimal balance;
}
