package com.example.loyalProgram.loyalPrograms.redeemPointsLoyalProgram;

import com.example.loyalProgram.loyalPrograms.baseLoyalProgram.LoyalProgram;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("REDEEM_POINTS")
public class RedeemPointsLoyalProgram extends LoyalProgram {
    private Integer priority;
}
