package com.example.loyalProgram.services;

import com.example.loyalProgram.DTOs.ClientDTO;
import com.example.loyalProgram.DTOs.MerchantDTO;
import com.example.loyalProgram.DTOs.TierDTO;

import java.util.List;


public interface AddingService {
    MerchantDTO addMerchant(MerchantDTO merchantDTO);

    List<TierDTO> addTiers(Integer id, List<TierDTO> tierDTOS);

   // List<LoyalProgramDTO> addLoyalPrograms(Integer tierId, List<LoyalProgramDTO> loyalProgramDTOs);
    List<ClientDTO> addClients(List<ClientDTO> clientDTOs);
    List<TierDTO> findAllTiers();
}
