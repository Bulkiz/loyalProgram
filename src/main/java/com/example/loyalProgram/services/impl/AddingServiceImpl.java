package com.example.loyalProgram.services.impl;

import com.example.loyalProgram.DTOs.ClientDTO;
import com.example.loyalProgram.DTOs.LoyalProgramDTO;
import com.example.loyalProgram.DTOs.MerchantDTO;
import com.example.loyalProgram.DTOs.TierDTO;
import com.example.loyalProgram.entities.LoyalProgram;
import com.example.loyalProgram.entities.Merchant;
import com.example.loyalProgram.entities.Tier;
import com.example.loyalProgram.repositories.LoyalProgramRepository;
import com.example.loyalProgram.repositories.MerchantRepository;
import com.example.loyalProgram.repositories.TierRepository;
import com.example.loyalProgram.services.AddingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddingServiceImpl implements AddingService {

    @Autowired private MerchantRepository merchantRepository;
    @Autowired private TierRepository tierRepository;
    @Autowired private LoyalProgramRepository loyalProgramRepository;
    @Autowired private ModelMapper modelMapper;
    @Override
    public ClientDTO addClient(ClientDTO client) {
        return null;
    }

    @Override
    public MerchantDTO addMerchant(MerchantDTO merchantDTO) {
        Merchant merchant = modelMapper.map(merchantDTO, Merchant.class);
        merchantRepository.save(merchant);
        return modelMapper.map(merchant, MerchantDTO.class);
    }

    @Override
    public List<TierDTO> addTiers(Integer id ,List<TierDTO> tierDTOS) {
        List<Tier> tiers = tierDTOS.parallelStream().map(tierDTO -> {
            Tier tier = modelMapper.map(tierDTO, Tier.class);
            tier.setMerchant(merchantRepository.findById(id).get());
            return tierRepository.save(tier);
        }).toList();
        return tiers.parallelStream().map(tier -> TierDTO.builder().id(tier.getId()).
                name(tier.getName()).tierAmount(tier.getTierAmount()).build()).toList();
    }

    @Override
    public List<LoyalProgramDTO> addLoyalPrograms(Integer tierId, List<LoyalProgramDTO> loyalProgramDTOs) {
        List<LoyalProgram> loyalPrograms = loyalProgramDTOs.parallelStream().map(loyalProgramDTO -> {
            LoyalProgram loyalProgram = modelMapper.map(loyalProgramDTO, LoyalProgram.class);
            loyalProgram.setTier(tierRepository.findById(tierId).get());
            return loyalProgramRepository.save(loyalProgram);
        }).toList();
        return loyalPrograms.parallelStream().map(loyalProgram -> modelMapper.
                map(loyalProgram, LoyalProgramDTO.class)).toList();
    }


//    @Override
//    public MerchantDTO addMerchant(MerchantDTO merchantDTO) {
//        List<Tier> tier = merchantDTO.getTiers().parallelStream().map(tierDTO -> {
//            Tier tier1 = modelMapper.map(tierDTO, Tier.class);
//            tier1.setMerchant(modelMapper.map(merchantDTO, Merchant.class));
//            tier1.getLoyalPrograms().parallelStream().forEach(loyalProgram -> loyalProgram.setTier(tier1));
//            return tier1;
//        }).toList();
//
//        return null;
//    }


}
