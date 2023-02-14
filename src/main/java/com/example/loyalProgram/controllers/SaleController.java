package com.example.loyalProgram.controllers;

import com.example.loyalProgram.DTOs.LoyalProgramDTO;
import com.example.loyalProgram.DTOs.MerchantDTO;
import com.example.loyalProgram.DTOs.TierDTO;
import com.example.loyalProgram.services.AddingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/merchant")
public class SaleController {

    @Autowired AddingService addingService;
    @PostMapping("/add")
    public MerchantDTO addMerchant(@RequestBody MerchantDTO merchantDTO){
        return addingService.addMerchant(merchantDTO);
    }

    @PostMapping("addTiers/{merchantId}")
    public List<TierDTO> addTiersToMerchant(@PathVariable Integer merchantId, @RequestBody List<TierDTO> tierDTOS){
        return addingService.addTiers(merchantId, tierDTOS);
    }

    @PostMapping("addLoyalProgram/{tierId}")
    public List<LoyalProgramDTO> addLoyalProgramsToTier(@PathVariable Integer tierId, @RequestBody List<LoyalProgramDTO> loyalProgramDTOs){
        return addingService.addLoyalPrograms(tierId, loyalProgramDTOs);
    }
}
