package com.example.loyalProgram.services.impl;

import com.example.loyalProgram.ClientModule.entities.Card;
import com.example.loyalProgram.ClientModule.entities.Client;
import com.example.loyalProgram.ClientModule.repositories.CardRepository;
import com.example.loyalProgram.ClientModule.repositories.ClientRepository;
import com.example.loyalProgram.MerchantModule.entities.LoyalProgram;
import com.example.loyalProgram.MerchantModule.entities.Merchant;
import com.example.loyalProgram.MerchantModule.entities.Tier;
import com.example.loyalProgram.MerchantModule.repositories.LoyalProgramRepository;
import com.example.loyalProgram.MerchantModule.repositories.MerchantRepository;
import com.example.loyalProgram.MerchantModule.repositories.TierRepository;
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
    public Merchant addMerchant(Merchant merchant) {
        return merchantRepository.save(merchant);
    }

    @Override
    public List<Tier> addTiers(Integer id , List<Tier> tier) {
        return tier.parallelStream().peek(currentTier -> {
            currentTier.setMerchant(merchantRepository.findById(id).orElseThrow());
            tierRepository.save(currentTier);
            List<LoyalProgram> loyalPrograms = currentTier.getLoyalPrograms().parallelStream().map(loyalProgramDTO -> {
                LoyalProgram loyalProgram = modelMapper.map(loyalProgramDTO, LoyalProgram.class);
                loyalProgram.setTier(currentTier);
                return loyalProgramRepository.save(loyalProgram);
            }).toList();
            currentTier.setLoyalPrograms(loyalPrograms);
        }).toList();
    }


    @Override
    public List<Tier> findAllTiers() {
        List<Tier> tiers = tierRepository.findAll();
        tiers.forEach(tier -> tier.setLoyalPrograms(loyalProgramRepository.findAllByTier(tier)));
        return tiers;
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
    public List<Client> addClients(List<Client> clients) {
       return clients.parallelStream().map(currentClient -> {
            currentClient.setAmountSpend(BigDecimal.ZERO);
            Card card = generateCard();
            currentClient.setCard(card);
            return clientRepository.save(currentClient);
        }).toList();
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
