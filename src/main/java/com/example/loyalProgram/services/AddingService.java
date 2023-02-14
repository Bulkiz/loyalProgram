package com.example.loyalProgram.services;

import com.example.loyalProgram.DTOs.ClientDTO;
import com.example.loyalProgram.DTOs.MerchantDTO;


public interface AddingService {

    ClientDTO addClient(ClientDTO client);
    MerchantDTO addMerchant(MerchantDTO merchant);
}
