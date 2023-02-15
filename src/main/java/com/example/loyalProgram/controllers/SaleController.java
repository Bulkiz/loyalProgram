package com.example.loyalProgram.controllers;

import com.example.loyalProgram.DTOs.ClientDTO;
import com.example.loyalProgram.DTOs.MerchantDTO;
import com.example.loyalProgram.DTOs.SaleDTO;
import com.example.loyalProgram.DTOs.TierDTO;
import com.example.loyalProgram.services.AddingService;
import com.example.loyalProgram.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/merchant")
public class SaleController {

    @Autowired AddingService addingService;
    @Autowired
    SaleService saleService;
    @PostMapping("/add")
    public MerchantDTO addMerchant(@RequestBody MerchantDTO merchantDTO){
        return addingService.addMerchant(merchantDTO);
    }

    @PostMapping("addTiers/{merchantId}")
    public List<TierDTO> addTiersToMerchant(@PathVariable Integer merchantId, @RequestBody List<TierDTO> tierDTOS){
        return addingService.addTiers(merchantId, tierDTOS);
    }

    @GetMapping("/allTiers")
    public List<TierDTO> findAllTiers(){
        return addingService.findAllTiers();
    }

    @PostMapping("addClients/")
    public List<ClientDTO> addClients(@RequestBody List<ClientDTO> clientDTOs){
        return addingService.addClients(clientDTOs);
    }
    @PostMapping("makeSale/")
    public void makeSale(@RequestBody SaleDTO saleDTO){
        saleService.makeSale(saleDTO);
    }
}
