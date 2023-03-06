package com.example.loyalProgram.loyalPrograms.baseLoyalProgram;

import com.example.loyalProgram.baseEntity.BaseEntity;
import com.example.loyalProgram.merchantModule.entities.Tier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING, columnDefinition = "varchar")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class LoyalProgram extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "tier_id")
    private Tier tier;
    private LocalDate expirationDate;

}
