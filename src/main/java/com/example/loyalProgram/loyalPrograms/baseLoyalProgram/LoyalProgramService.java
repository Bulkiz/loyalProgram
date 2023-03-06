package com.example.loyalProgram.loyalPrograms.baseLoyalProgram;

import com.example.loyalProgram.saleModule.entities.Sale;

public interface LoyalProgramService<T extends LoyalProgram> {

    Sale applyProgram(Sale sale, T loyalProgram);
}
