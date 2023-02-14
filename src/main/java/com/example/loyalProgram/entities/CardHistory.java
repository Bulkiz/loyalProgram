package com.example.loyalProgram.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardHistory extends BaseEntity{
    @OneToOne
    @JoinColumn(name = "card_id")
    private Card card;
    @Column(columnDefinition = "int2")
    private TransactionStatus status;
    private Integer points;
    private LocalDateTime earnDate;
    private LocalDateTime expirationDate;

}
