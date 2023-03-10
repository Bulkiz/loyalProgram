package com.example.loyalProgram.enums;

import com.example.loyalProgram.loyalPrograms.addPointsLoyalProgram.AddPointsLoyalProgram;
import com.example.loyalProgram.loyalPrograms.baseLoyalProgram.LoyalProgram;
import com.example.loyalProgram.loyalPrograms.birthdayLoyalProgram.BirthdayLoyalProgram;
import com.example.loyalProgram.loyalPrograms.discountLoyalProgram.DiscountLoyalProgram;
import com.example.loyalProgram.loyalPrograms.usePointsLoyalProgram.UsePointsLoyalProgram;
import lombok.Getter;

@Getter
public enum LoyalProgramType {
    ADD_POINTS(AddPointsLoyalProgram.class), DISCOUNT(DiscountLoyalProgram.class),
    BIRTHDAY(BirthdayLoyalProgram.class), USE_POINTS(UsePointsLoyalProgram.class);
    private final Class<? extends LoyalProgram> clazz;

    LoyalProgramType(Class<? extends LoyalProgram> clazz) {
        this.clazz = clazz;
    }



}
