package com.example.loyalProgram.merchantModule.entities.services;

import com.example.loyalProgram.merchantModule.entities.loyals.LoyalProgram;
import com.example.loyalProgram.saleModule.entities.Sale;

public interface LoyalProgramService<T extends LoyalProgram> {

    Sale applyProgram(Sale sale, T loyalProgram);
}
