package com.example.loyalProgram.basePackage;

import com.example.loyalProgram.clientModule.entities.Card;
import com.example.loyalProgram.clientModule.entities.Client;
import com.example.loyalProgram.clientModule.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class GenerateCard {
    @Autowired CardRepository cardRepository;
    public void generateAndSetCard(Client client) {
        Card card = new Card();
        List<Card> currCards = cardRepository.findByClient(client);
        card.setIsActive(true);
        currCards.add(card);
        card.setClient(client);
        client.setCards(currCards);
        cardRepository.save(card);
    }
}
