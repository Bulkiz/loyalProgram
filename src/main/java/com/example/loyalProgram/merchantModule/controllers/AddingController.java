package com.example.loyalProgram.merchantModule.controllers;

import com.example.loyalProgram.clientModule.DTOs.ClientDTO;
import com.example.loyalProgram.merchantModule.DTOs.MerchantDTO;
import com.example.loyalProgram.merchantModule.DTOs.TierDTO;
import com.example.loyalProgram.clientModule.entities.Client;
import com.example.loyalProgram.merchantModule.entities.loyals.LoyalProgram;
import com.example.loyalProgram.merchantModule.entities.Merchant;
import com.example.loyalProgram.merchantModule.entities.Tier;
import com.example.loyalProgram.merchantModule.services.AddingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/merchant")
public class AddingController {

    @Autowired AddingService addingService;
    @Autowired ModelMapper modelMapper;

    @PostMapping("/add")
    public MerchantDTO addMerchant(@RequestBody MerchantDTO merchantDTO){
        return modelMapper.map(addingService.addMerchant(modelMapper.map(merchantDTO, Merchant.class)), MerchantDTO.class);
    }

    @PostMapping("addTiers/{merchantId}")
    public List<TierDTO> addTiersToMerchant(@PathVariable Integer merchantId, @RequestBody List<TierDTO> tierDTOS){
        return addingService.addTiers(merchantId, tierDTOS.parallelStream().
                map(tierDTO -> {
                   List<LoyalProgram> loyalPrograms = tierDTO.getLoyalPrograms().parallelStream()
                            .map(loyalProgramDTO ->
                                modelMapper.map(loyalProgramDTO, LoyalProgram.class)).toList();

                    Tier tier = modelMapper.map(tierDTO, Tier.class);
                    tier.setLoyalPrograms(loyalPrograms);
                    return tier;
                }).toList()).parallelStream().
                map(tier -> modelMapper.map(tier, TierDTO.class)).toList();
    }

    @GetMapping("/allTiers")
    public List<TierDTO> findAllTiers(){
        return addingService.findAllTiers().parallelStream().
                map(tier -> modelMapper.map(tier, TierDTO.class)).toList();
    }

    @PostMapping("/addClients")
    public List<ClientDTO> addClients(@RequestBody List<ClientDTO> clientDTOs){
        return addingService.addClients(clientDTOs.parallelStream().
                map(clientDTO -> modelMapper.map(clientDTO, Client.class)).toList()).parallelStream().
                map(client -> modelMapper.map(client, ClientDTO.class)).toList();
    }
}
