package com.example.loyalProgram.entities;

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
public class Sale extends BaseEntity{

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

}
