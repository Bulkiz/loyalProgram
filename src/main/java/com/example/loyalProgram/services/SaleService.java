package com.example.loyalProgram.services;

import java.math.BigDecimal;

public interface SaleService {

    void makeSale(Integer clientId, BigDecimal price);
}
