package com.example.loyalProgram.merchantModule.entities;

import com.example.loyalProgram.baseEntity.BaseEntity;
import com.example.loyalProgram.enums.LoyalProgramType;
import jakarta.persistence.Column;
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
public class LoyalProgram extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "tier_id")
    private Tier tier;
    private String name;
    private Integer priority;
    @Column(columnDefinition = "numeric(19, 2)")
    private BigDecimal discountPercentage;
    @Column(columnDefinition = "int2")
    private LoyalProgramType type;
}