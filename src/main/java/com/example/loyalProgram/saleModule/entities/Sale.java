package com.example.loyalProgram.saleModule.entities;

import com.example.loyalProgram.basePackage.BaseEntity;
import com.example.loyalProgram.clientModule.entities.Card;
import com.example.loyalProgram.clientModule.entities.Client;
import com.example.loyalProgram.merchantModule.entities.Merchant;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

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
    @OneToOne
    @JoinColumn(name = "card_id")
    private Card card;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Sale sale = (Sale) o;
        return getId() != null && Objects.equals(getId(), sale.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
