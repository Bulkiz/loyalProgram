package com.example.loyalProgram.saleModule.services;

import com.example.loyalProgram.saleModule.entities.Sale;

import java.math.BigDecimal;

public interface SaleService {
    BigDecimal makeSale(Sale sale);
}
