package com.example.loyalProgram.merchantModule.entities.loyals;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("BIRTHDAY")
public class BirthdayLoyalProgram extends LoyalProgram{
}
