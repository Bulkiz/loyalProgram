package com.example.loyalProgram.services.impl;

import com.example.loyalProgram.DTOs.SaleDTO;
import com.example.loyalProgram.entities.Sale;
import com.example.loyalProgram.repositories.ClientRepository;
import com.example.loyalProgram.repositories.MerchantRepository;
import com.example.loyalProgram.repositories.SaleRepository;
import com.example.loyalProgram.services.SaleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired ClientRepository clientRepository;
    @Autowired MerchantRepository merchantRepository;
    @Autowired SaleRepository saleRepository;
    @Autowired ModelMapper modelMapper;

    @Override
    public void makeSale(SaleDTO saleDTO) {
        Sale sale = new Sale();
        sale.setClient(clientRepository.findById(saleDTO.getClientId()).get());
        //sale.se

    }
}
