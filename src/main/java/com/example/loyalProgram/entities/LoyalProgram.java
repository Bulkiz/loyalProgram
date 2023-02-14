package com.example.loyalProgram.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoyalProgram extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "tier_id")
    private Tier tier;
    private Integer priority;
    private BigDecimal discountPercentage;
}
