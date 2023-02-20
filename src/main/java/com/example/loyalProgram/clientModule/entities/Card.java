package com.example.loyalProgram.clientModule.entities;

import com.example.loyalProgram.baseEntity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Card extends BaseEntity {
    @Column(columnDefinition = "numeric(19, 2)")
    private BigDecimal balance = BigDecimal.ZERO;
}
