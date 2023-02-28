package com.example.loyalProgram.merchantModule.entities;

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
public class DiscountLoyalProgram extends PriorityLoyalProgram{

    @Column(columnDefinition = "numeric(19, 2)")
    private BigDecimal discountPercentage;
}
