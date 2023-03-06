package com.example.loyalProgram.merchantModule.services.impl;

import com.example.loyalProgram.clientModule.entities.Card;
import com.example.loyalProgram.clientModule.entities.Client;
import com.example.loyalProgram.clientModule.repositories.CardRepository;
import com.example.loyalProgram.clientModule.repositories.ClientRepository;
import com.example.loyalProgram.merchantModule.entities.*;
import com.example.loyalProgram.merchantModule.entities.loyals.AddPointsLoyalProgram;
import com.example.loyalProgram.merchantModule.entities.loyals.BirthdayLoyalProgram;
import com.example.loyalProgram.merchantModule.entities.loyals.DiscountLoyalProgram;
import com.example.loyalProgram.merchantModule.repositories.LoyalProgramRepository;
import com.example.loyalProgram.merchantModule.repositories.MerchantRepository;
import com.example.loyalProgram.merchantModule.repositories.TierRepository;
import com.example.loyalProgram.merchantModule.services.AddingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class AddingServiceImpl implements AddingService {

    @Autowired private MerchantRepository merchantRepository;
    @Autowired private TierRepository tierRepository;
    @Autowired private LoyalProgramRepository loyalProgramRepository;
    @Autowired private ClientRepository clientRepository;
    @Autowired private CardRepository cardRepository;

    @Override
    public Merchant addMerchant(Merchant merchant) {
       loyalProgramRepository.save(new DiscountLoyalProgram(BigDecimal.valueOf(10), BigDecimal.valueOf(10)));
        return merchantRepository.save(merchant);
    }

    @Override
    public List<Tier> addTiers(Integer id, List<Tier> tier) {
        return tier.parallelStream().peek(currentTier -> {
            currentTier.setMerchant(merchantRepository.findById(id).orElseThrow());
            tierRepository.save(currentTier);
            currentTier.getLoyalPrograms().forEach(loyalProgram -> loyalProgram.setTier(currentTier));
            loyalProgramRepository.saveAll(currentTier.getLoyalPrograms());
        }).toList();
    }

    @Override
    public List<Tier> findAllTiers() {
        List<Tier> tiers = tierRepository.findAll();
        tiers.forEach(tier -> tier.setLoyalPrograms(loyalProgramRepository.findAllByTier(tier)));
        return tiers;
    }

    @Override
    public List<Client> addClients(List<Client> clients) {
        return clients.parallelStream().peek(currentClient -> {
            clientRepository.save(currentClient);
            generateAndSetCard(currentClient);
        }).toList();
    }

    private void generateAndSetCard(Client client) {
        Card card = new Card();
        List<Card> currCards = client.getCards();
        card.setIsActive(true);
        currCards.add(card);
        card.setClient(client);
        client.setCards(currCards);
        cardRepository.save(card);
    }
}
