package com.example.loyalProgram.clientModule.services.impl;

import com.example.loyalProgram.clientModule.entities.Card;
import com.example.loyalProgram.clientModule.repositories.CardRepository;
import com.example.loyalProgram.clientModule.repositories.ClientRepository;
import com.example.loyalProgram.clientModule.services.ClientService;
import com.example.loyalProgram.basePackage.GenerateCardAndSaleBonus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired private GenerateCardAndSaleBonus generateCardAndSaleBonus;
    @Autowired private ClientRepository clientRepository;
    @Autowired private CardRepository cardRepository;

    @Override
    public void addCard(Integer clientId) {
       generateCardAndSaleBonus.generateAndSetCard(clientRepository.findById(clientId).orElseThrow());
    }

    @Override
    public void disableCard(Integer cardId) {
        Card card = cardRepository.findById(cardId).orElseThrow();
        card.setIsActive(false);
        cardRepository.save(card);
    }


}
