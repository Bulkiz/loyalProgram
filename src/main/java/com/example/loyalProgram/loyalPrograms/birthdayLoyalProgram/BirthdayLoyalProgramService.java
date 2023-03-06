package com.example.loyalProgram.loyalPrograms.birthdayLoyalProgram;

import com.example.loyalProgram.saleModule.entities.Sale;
import com.example.loyalProgram.loyalPrograms.baseLoyalProgram.LoyalProgramService;
import org.springframework.stereotype.Service;

@Service
public class BirthdayLoyalProgramService implements LoyalProgramService<BirthdayLoyalProgram> {
    @Override
    public Sale applyProgram(Sale sale, BirthdayLoyalProgram loyalProgram) {
        return null;
    }
}
