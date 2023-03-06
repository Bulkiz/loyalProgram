package com.example.loyalProgram.loyalPrograms.discountLoyalProgram;

import com.example.loyalProgram.loyalPrograms.baseLoyalProgram.LoyalProgram;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("DISCOUNT")
public class DiscountLoyalProgram extends LoyalProgram {
    @Column(columnDefinition = "numeric(19, 2)")
    private BigDecimal discountPercentage;
    private Integer priority;

}
