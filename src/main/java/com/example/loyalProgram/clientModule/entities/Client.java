package com.example.loyalProgram.clientModule.entities;

import com.example.loyalProgram.merchantModule.entities.Merchant;
import com.example.loyalProgram.merchantModule.entities.Tier;
import com.example.loyalProgram.saleModule.entities.Sale;
import com.example.loyalProgram.baseEntity.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client extends BaseEntity {
    private String name;

    private LocalDate birthday;
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
    private BigDecimal amountSpend = BigDecimal.ZERO;
}
