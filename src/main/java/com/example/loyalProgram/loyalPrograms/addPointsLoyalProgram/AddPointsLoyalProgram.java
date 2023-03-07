package com.example.loyalProgram.loyalPrograms.addPointsLoyalProgram;

import com.example.loyalProgram.loyalPrograms.baseLoyalProgram.LoyalProgram;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DiscriminatorValue("ADD_POINTS")
public class AddPointsLoyalProgram extends LoyalProgram {
    private Integer priority;
    private BigDecimal scale;
    @Column(columnDefinition = "numeric(19, 2)")
    private BigDecimal discountPercentage;
}
