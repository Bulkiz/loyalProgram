package com.example.loyalProgram.loyalPrograms.usePointsLoyalProgram;

import com.example.loyalProgram.loyalPrograms.baseLoyalProgram.LoyalProgram;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("USE_POINTS")
public class UsePointsLoyalProgram extends LoyalProgram {
    private Integer priority;
}
