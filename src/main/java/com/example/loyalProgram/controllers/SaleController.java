package com.example.loyalProgram.controllers;

import com.example.loyalProgram.DTOs.ClientDTO;
import com.example.loyalProgram.DTOs.MerchantDTO;
import com.example.loyalProgram.DTOs.TierDTO;
import com.example.loyalProgram.entities.Client;
import com.example.loyalProgram.entities.Merchant;
import com.example.loyalProgram.entities.Tier;
import com.example.loyalProgram.services.AddingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/merchant")
public class SaleController {

    @Autowired AddingService addingService;
    @Autowired ModelMapper modelMapper;
    @PostMapping("/add")
    public MerchantDTO addMerchant(@RequestBody MerchantDTO merchantDTO){
        return modelMapper.map(addingService.addMerchant(modelMapper.map(merchantDTO, Merchant.class)), MerchantDTO.class);
    }

    @PostMapping("addTiers/{merchantId}")
    public List<TierDTO> addTiersToMerchant(@PathVariable Integer merchantId, @RequestBody List<TierDTO> tierDTOS){
        return addingService.addTiers(merchantId, tierDTOS.parallelStream().
                map(tier -> modelMapper.map(tier, Tier.class)).toList()).parallelStream().
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
