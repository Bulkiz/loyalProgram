package com.example.loyalProgram.clientModule.entities;

import com.example.loyalProgram.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Card extends BaseEntity {
    @Column(columnDefinition = "numeric(19, 2)")
    private BigDecimal balance = BigDecimal.ZERO;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    private Boolean isActive;
}

