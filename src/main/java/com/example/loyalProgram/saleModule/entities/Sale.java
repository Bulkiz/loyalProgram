package com.example.loyalProgram.saleModule.entities;

import com.example.loyalProgram.clientModule.entities.Client;
import com.example.loyalProgram.merchantModule.entities.Merchant;
import com.example.loyalProgram.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sale extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;
    @Column(columnDefinition = "numeric(19, 2)")
    private BigDecimal originalPrice;
    @Column(columnDefinition = "numeric(19, 2)")
    private BigDecimal discountedPrice;
    @Column(columnDefinition = "numeric(19, 2)")
    private BigDecimal summaryPrice;
    @OneToMany(mappedBy = "sale")
    private List<SaleBonus> saleBonusList;
    @Column(columnDefinition = "numeric(19, 2)")
    private BigDecimal usedPoints;

}
