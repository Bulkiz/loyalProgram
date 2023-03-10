package com.example.loyalProgram.merchantModule.services.impl;

import com.example.loyalProgram.basePackage.GenerateCardAndSaleBonus;
import com.example.loyalProgram.clientModule.entities.Client;
import com.example.loyalProgram.clientModule.repositories.ClientRepository;
import com.example.loyalProgram.merchantModule.entities.Merchant;
import com.example.loyalProgram.merchantModule.entities.Tier;
import com.example.loyalProgram.loyalPrograms.baseLoyalProgram.LoyalProgramRepository;
import com.example.loyalProgram.merchantModule.repositories.MerchantRepository;
import com.example.loyalProgram.merchantModule.repositories.TierRepository;
import com.example.loyalProgram.merchantModule.services.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired private MerchantRepository merchantRepository;
    @Autowired private TierRepository tierRepository;
    @Autowired private LoyalProgramRepository loyalProgramRepository;
    @Autowired private ClientRepository clientRepository;
    @Autowired private GenerateCardAndSaleBonus generateCardAndSaleBonus;

    @Override
    public Merchant addMerchant(Merchant merchant) {
        return merchantRepository.save(merchant);
    }

    @Override
    public List<Tier> addTiers(Integer id, List<Tier> tier) {
        return tier.parallelStream().peek(currentTier -> {
            currentTier.setMerchant(merchantRepository.findById(id).orElseThrow());
            tierRepository.save(currentTier);
            currentTier.getLoyalPrograms().forEach(loyalProgram -> {
                loyalProgram.setTier(currentTier);
                loyalProgram.setExpirationDate(LocalDate.now().plusMonths(1));
            });
            loyalProgramRepository.saveAll(currentTier.getLoyalPrograms());
        }).toList();
    }

    @Override
    public List<Tier> findAllTiers() {
        List<Tier> tiers = tierRepository.findAll();
        tiers.forEach(tier -> tier.setLoyalPrograms(loyalProgramRepository.findAllByTier(tier.getId())));
        return tiers;
    }

    @Override
    public List<Client> addClients(Integer merchantId, List<Client> clients) {
        return clients.parallelStream().peek(currentClient -> {
            currentClient.setMerchant(merchantRepository.findById(merchantId).orElseThrow());
            clientRepository.save(currentClient);
            generateCardAndSaleBonus.generateAndSetCard(currentClient);
        }).toList();
    }

}
