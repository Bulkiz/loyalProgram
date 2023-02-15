package com.example.loyalProgram.DTOs;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TierDTO {
    private Integer id;
    private String name;
    private BigDecimal tierAmount;
    private List<LoyalProgramDTO> loyalPrograms;
}
