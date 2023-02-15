package com.example.loyalProgram.ClientModule.entities;

import com.example.loyalProgram.MerchantModule.entities.Merchant;
import com.example.loyalProgram.MerchantModule.entities.Tier;
import com.example.loyalProgram.SaleModule.entities.Sale;
import com.example.loyalProgram.BaseEntity.*;
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
public class Client extends BaseEntity {
    private String name;
    @OneToMany(mappedBy = "client")
    private List<Sale> sales;
    @OneToOne
    @JoinColumn(name = "card_id")
    private Card card;
    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;
    @OneToOne
    @JoinColumn(name = "tier_id")
    private Tier tier;
    @Column(columnDefinition = "numeric(19, 2)")
    private BigDecimal amountSpend;
}
