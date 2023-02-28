package com.example.loyalProgram.merchantModule.entities;

import jakarta.persistence.MappedSuperclass;
import lombok.*;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriorityLoyalProgram extends LoyalProgram{
    private Integer priority;
}
