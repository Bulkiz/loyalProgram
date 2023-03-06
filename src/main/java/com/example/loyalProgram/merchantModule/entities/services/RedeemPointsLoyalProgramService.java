package com.example.loyalProgram.merchantModule.entities.services;

import com.example.loyalProgram.merchantModule.entities.loyals.RedeemPointsLoyalProgram;
import com.example.loyalProgram.saleModule.entities.Sale;
import org.springframework.stereotype.Service;

@Service
public class RedeemPointsLoyalProgramService implements LoyalProgramService<RedeemPointsLoyalProgram> {
    @Override
    public Sale applyProgram(Sale sale, RedeemPointsLoyalProgram loyalProgram) {
        return null;
    }
}
