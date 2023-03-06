package com.example.loyalProgram.merchantModule.entities.loyals;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("REDEEM_POINTS")
public class RedeemPointsLoyalProgram extends LoyalProgram{
    private BigDecimal priority;
}
