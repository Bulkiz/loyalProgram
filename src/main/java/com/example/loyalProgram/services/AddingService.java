package com.example.loyalProgram.services;

import com.example.loyalProgram.clientModule.entities.Client;
import com.example.loyalProgram.merchantModule.entities.Merchant;
import com.example.loyalProgram.merchantModule.entities.Tier;

import java.util.List;


public interface AddingService {
    Merchant addMerchant(Merchant merchant);

    List<Tier> addTiers(Integer id, List<Tier> tier);

   // List<LoyalProgramDTO> addLoyalPrograms(Integer tierId, List<LoyalProgramDTO> loyalProgramDTOs);
    List<Client> addClients(List<Client> client);
    List<Tier> findAllTiers();
}
