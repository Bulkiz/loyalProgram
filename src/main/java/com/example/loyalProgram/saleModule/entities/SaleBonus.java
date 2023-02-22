package com.example.loyalProgram.saleModule.entities;

import com.example.loyalProgram.merchantModule.entities.LoyalProgram;
import com.example.loyalProgram.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleBonus extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;
    @OneToOne
    @JoinColumn(name = "loyal_program_id")
    private LoyalProgram loyalProgram;
    @Column(columnDefinition = "int2")
    private BigDecimal currentPrice;
    private BigDecimal savedMoney;
}
