package com.example.loyalProgram.services;

import com.example.loyalProgram.DTOs.ClientDTO;
import com.example.loyalProgram.DTOs.LoyalProgramDTO;
import com.example.loyalProgram.DTOs.MerchantDTO;
import com.example.loyalProgram.DTOs.TierDTO;

import java.util.List;


public interface AddingService {

    ClientDTO addClient(ClientDTO clientDTO);
    MerchantDTO addMerchant(MerchantDTO merchantDTO);

    List<TierDTO> addTiers(Integer id, List<TierDTO> tierDTOS);

    List<TierDTO> findAllTiers();
}
