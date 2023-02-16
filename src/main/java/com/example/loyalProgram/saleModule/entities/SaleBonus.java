package com.example.loyalProgram.saleModule.entities;

import com.example.loyalProgram.merchantModule.entities.LoyalProgram;
import com.example.loyalProgram.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SaleBonus extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;
    @OneToOne
    @JoinColumn(name = "loyal_program_id")
    private LoyalProgram loyalProgram;
    @Column(columnDefinition = "int2")
    private BigDecimal currentPrice;
    private BigDecimal savedMoved;
}