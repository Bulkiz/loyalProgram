package com.example.loyalProgram.services.impl;

import com.example.loyalProgram.SaleModule.DTOs.SaleDTO;
import com.example.loyalProgram.ClientModule.repositories.ClientRepository;
import com.example.loyalProgram.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired ClientRepository clientRepository;

    @Override
    public void makeSale(SaleDTO sale) {

    }
}
