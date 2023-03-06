package com.example.loyalProgram.merchantModule.entities.loyals;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("ADD_POINTS")
public class AddPointsLoyalProgram extends LoyalProgram{
    private Integer priority;
    private BigDecimal scale;
}
