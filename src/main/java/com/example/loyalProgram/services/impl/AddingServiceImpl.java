package com.example.loyalProgram.services.impl;

import com.example.loyalProgram.DTOs.ClientDTO;
import com.example.loyalProgram.DTOs.MerchantDTO;
import com.example.loyalProgram.DTOs.TierDTO;
import com.example.loyalProgram.entities.*;
import com.example.loyalProgram.repositories.*;
import com.example.loyalProgram.services.AddingService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class AddingServiceImpl implements AddingService {

    @Autowired private MerchantRepository merchantRepository;
    @Autowired private TierRepository tierRepository;
    @Autowired private LoyalProgramRepository loyalProgramRepository;
    @Autowired private ClientRepository clientRepository;
    @Autowired private CardRepository cardRepository;
    @Autowired private ModelMapper modelMapper;
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
            tierRepository.save(tier);
            List<LoyalProgram> loyalPrograms = tierDTO.getLoyalPrograms().parallelStream().map(loyalProgramDTO -> {
                LoyalProgram loyalProgram = modelMapper.map(loyalProgramDTO, LoyalProgram.class);
                loyalProgram.setTier(tier);
                return loyalProgramRepository.save(loyalProgram);
            }).toList();
            tier.setLoyalPrograms(loyalPrograms);
            return tier;
        }).toList();
        return tiers.parallelStream().map(tier -> modelMapper.map(tier, TierDTO.class)).toList();
    }


    @Override
    public List<TierDTO> findAllTiers() {
        return tierRepository.findAll().parallelStream().map(tier -> {
            tier.setLoyalPrograms(loyalProgramRepository.findAllByTier(tier));
            return modelMapper.map(tier, TierDTO.class);
        }).toList();
    }

//    @Override
//    public List<LoyalProgramDTO> addLoyalPrograms(Integer tierId, List<LoyalProgramDTO> loyalProgramDTOs) {
//        List<LoyalProgram> loyalPrograms = loyalProgramDTOs.parallelStream().map(loyalProgramDTO -> {
//            LoyalProgram loyalProgram = modelMapper.map(loyalProgramDTO, LoyalProgram.class);
//            loyalProgram.setTier(tierRepository.findById(tierId).get());
//            return loyalProgramRepository.save(loyalProgram);
//        }).toList();
//        return loyalPrograms.parallelStream().map(loyalProgram -> modelMapper.
//                map(loyalProgram, LoyalProgramDTO.class)).toList();
//    }

    @Override
    public List<ClientDTO> addClients(List<ClientDTO> clientDTOs) {
        List<Client> clients = clientDTOs.parallelStream().map(clientDTO -> {
            Client client = modelMapper.map(clientDTO,Client.class);
            client.setAmountSpend(BigDecimal.ZERO);
            Card card = generateCard();
            client.setCard(card);
            return clientRepository.save(client);
        }).toList();
        return clients.parallelStream().map(client -> modelMapper.map(client, ClientDTO.class)).toList();
    }

    private Card generateCard(){
        Card card = new Card();
        card.setBalance(BigDecimal.ZERO);
        return cardRepository.save(card);
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
