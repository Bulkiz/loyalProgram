package com.example.loyalProgram.merchantModule.controllers;

import com.example.loyalProgram.clientModule.DTOs.ClientDTO;
import com.example.loyalProgram.clientModule.entities.Client;
import com.example.loyalProgram.enums.LoyalProgramType;
import com.example.loyalProgram.exceptionHandlingAndValidation.ValidateRequestBodyList;
import com.example.loyalProgram.loyalPrograms.baseLoyalProgram.LoyalProgram;
import com.example.loyalProgram.merchantModule.DTOs.MerchantDTO;
import com.example.loyalProgram.merchantModule.DTOs.TierDTO;
import com.example.loyalProgram.merchantModule.entities.Merchant;
import com.example.loyalProgram.merchantModule.entities.Tier;
import com.example.loyalProgram.merchantModule.services.MerchantService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Autowired
    MerchantService addingService;
    @Autowired ModelMapper modelMapper;

    @PostMapping
    public MerchantDTO addMerchant(@Valid @RequestBody MerchantDTO merchantDTO){
        return modelMapper.map(addingService.addMerchant(modelMapper.map(merchantDTO, Merchant.class)), MerchantDTO.class);
    }

    @PostMapping("/{merchantId}/addTiers")
    public List<TierDTO> addTiersToMerchant(@PathVariable Integer merchantId,
                                            @Valid @RequestBody ValidateRequestBodyList<TierDTO> tierDTOS){
        return addingService.addTiers(merchantId, tierDTOS.getRequestBody().parallelStream().
                map(tierDTO -> {
                   List<LoyalProgram> loyalPrograms = tierDTO.getLoyalPrograms().parallelStream()
                            .map(loyalProgramDTO ->
                               (LoyalProgram) modelMapper.map(loyalProgramDTO,
                                       LoyalProgramType.valueOf(loyalProgramDTO.getType().toUpperCase()).getClazz())
                            ).toList();

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

    @PostMapping("{merchantId}/addClients")
    public List<ClientDTO> addClients(@PathVariable Integer merchantId,
                                      @Valid @RequestBody ValidateRequestBodyList<ClientDTO> clientDTOs){
        return addingService.addClients(merchantId, clientDTOs.getRequestBody().parallelStream().
                map(clientDTO -> modelMapper.map(clientDTO, Client.class)).toList()).parallelStream().
                map(client -> modelMapper.map(client, ClientDTO.class)).toList();
    }
}
