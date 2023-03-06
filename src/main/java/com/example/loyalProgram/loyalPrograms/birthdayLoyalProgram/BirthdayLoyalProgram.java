package com.example.loyalProgram.loyalPrograms.birthdayLoyalProgram;

import com.example.loyalProgram.loyalPrograms.baseLoyalProgram.LoyalProgram;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("BIRTHDAY")
public class BirthdayLoyalProgram extends LoyalProgram {
}
