package com.example.loyalProgram.merchantModule.entities;

import com.example.loyalProgram.baseEntity.BaseEntity;
import com.example.loyalProgram.merchantModule.entities.loyals.LoyalProgram;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tier extends BaseEntity {
    private String name;
    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;
    @OneToMany(mappedBy = "tier")
    private List<LoyalProgram> loyalPrograms;
    @Column(columnDefinition = "numeric(19, 2)")
    private BigDecimal tierAmount;
}
