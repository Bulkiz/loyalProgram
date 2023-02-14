package com.example.loyalProgram.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tier extends BaseEntity{
    private String name;
    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;
    @OneToMany(mappedBy = "tier")
    private List<LoyalProgram> loyalPrograms;
    @Column(columnDefinition = "numeric(19, 2)")
    private BigDecimal tierAmount;
}
