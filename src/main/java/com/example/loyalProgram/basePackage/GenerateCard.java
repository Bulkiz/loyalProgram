package com.example.loyalProgram.basePackage;

import com.example.loyalProgram.clientModule.entities.Card;
import com.example.loyalProgram.clientModule.entities.Client;
import com.example.loyalProgram.clientModule.repositories.CardRepository;
import com.example.loyalProgram.loyalPrograms.baseLoyalProgram.LoyalProgram;
import com.example.loyalProgram.loyalPrograms.discountLoyalProgram.DiscountLoyalProgram;
import com.example.loyalProgram.saleModule.entities.Sale;
import com.example.loyalProgram.saleModule.entities.SaleBonus;
import com.example.loyalProgram.saleModule.repositories.SaleBonusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class GenerateCard {
    @Autowired private CardRepository cardRepository;
    @Autowired private SaleBonusRepository saleBonusRepository;

    public void generateAndSetCard(Client client) {
        Card card = new Card();
        List<Card> currCards = cardRepository.findByClient(client);
        card.setIsActive(true);
        currCards.add(card);
        card.setClient(client);
        client.setCards(currCards);
        cardRepository.save(card);
    }

    public <T extends LoyalProgram> void generateSaleBonus(Sale sale, T loyalProgram) {
         saleBonusRepository.save(SaleBonus.builder()
                .loyalProgram(loyalProgram)
                .sale(sale)
                .currentPrice(sale.getSummaryPrice())
                .savedMoney(sale.getDiscountedPrice())
                .build());
    }
}
