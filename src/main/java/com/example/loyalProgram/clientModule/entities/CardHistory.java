package com.example.loyalProgram.clientModule.entities;

import com.example.loyalProgram.baseEntity.BaseEntity;
import com.example.loyalProgram.enums.PointStatus;
import com.example.loyalProgram.enums.TransactionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardHistory extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "card_id")
    private Card card;
    @Column(columnDefinition = "int2")
    private TransactionStatus transactionStatus;
    @Column(columnDefinition = "int2")
    private PointStatus pointStatus;
    @Column(columnDefinition = "numeric(19, 2)")
    private BigDecimal points;
    private LocalDateTime earnDate;
    private LocalDateTime expirationDate;
    @OneToOne
    @JoinColumn(name = "points_ref_id")
    private CardHistory pointsRef;

}
