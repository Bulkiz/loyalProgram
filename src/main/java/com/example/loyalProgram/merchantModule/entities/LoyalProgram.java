package com.example.loyalProgram.merchantModule.entities;

import com.example.loyalProgram.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING, columnDefinition = "varchar")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class LoyalProgram extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "tier_id")
    private Tier tier;
    private LocalDate expirationDate;
}
