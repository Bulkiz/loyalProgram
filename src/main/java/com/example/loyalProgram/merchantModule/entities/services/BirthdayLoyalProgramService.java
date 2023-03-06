package com.example.loyalProgram.merchantModule.entities.services;

import com.example.loyalProgram.merchantModule.entities.loyals.BirthdayLoyalProgram;
import com.example.loyalProgram.saleModule.entities.Sale;
import org.springframework.stereotype.Service;

@Service
public class BirthdayLoyalProgramService implements LoyalProgramService<BirthdayLoyalProgram> {
    @Override
    public Sale applyProgram(Sale sale, BirthdayLoyalProgram loyalProgram) {
        return null;
    }
}
