package com.example.loyalProgram.saleModule.DTOs;

import com.example.loyalProgram.clientModule.entities.Card;
import com.example.loyalProgram.clientModule.entities.Client;
import com.example.loyalProgram.clientModule.repositories.CardRepository;
import com.example.loyalProgram.clientModule.repositories.ClientRepository;
import com.example.loyalProgram.merchantModule.entities.Merchant;
import com.example.loyalProgram.merchantModule.repositories.MerchantRepository;
import com.example.loyalProgram.saleModule.entities.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaleMapper {

    @Autowired private ClientRepository clientRepository;
    @Autowired private MerchantRepository merchantRepository;
    @Autowired
    private CardRepository cardRepository;

    public Sale saleMapper(SaleDTO saleDTO){
        Sale sale = new Sale();
        Client client = clientRepository.findById(saleDTO.getClientId()).orElseThrow();
        Merchant merchant = merchantRepository.findById(saleDTO.getMerchantId()).orElseThrow();
        Card card = cardRepository.findById(saleDTO.getCardId()).orElseThrow();
        sale.setMerchant(merchant);
        sale.setClient(client);
        sale.setCard(card);
        sale.setOriginalPrice(saleDTO.getPrice());
        sale.setUsedPoints(saleDTO.getUsedPoints());
        return sale;
    }
}
