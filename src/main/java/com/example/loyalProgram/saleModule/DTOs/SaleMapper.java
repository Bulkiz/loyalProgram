package com.example.loyalProgram.saleModule.DTOs;

import com.example.loyalProgram.clientModule.entities.Card;
import com.example.loyalProgram.clientModule.entities.Client;
import com.example.loyalProgram.clientModule.repositories.CardRepository;
import com.example.loyalProgram.clientModule.repositories.ClientRepository;
import com.example.loyalProgram.exceptionHandlingAndValidation.notFoundExceptions.CardNotFoundException;
import com.example.loyalProgram.exceptionHandlingAndValidation.notFoundExceptions.ClientNotFoundException;
import com.example.loyalProgram.exceptionHandlingAndValidation.notFoundExceptions.MerchantNotFoundException;
import com.example.loyalProgram.merchantModule.entities.Merchant;
import com.example.loyalProgram.merchantModule.repositories.MerchantRepository;
import com.example.loyalProgram.saleModule.entities.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SaleMapper {

    @Autowired private ClientRepository clientRepository;
    @Autowired private MerchantRepository merchantRepository;
    @Autowired
    private CardRepository cardRepository;

    public Sale mapInput(InputSaleDTO inputSaleDTO){
        Client client = clientRepository.findById(inputSaleDTO.getClientId()).orElseThrow(ClientNotFoundException::new);
        Merchant merchant = merchantRepository.findById(inputSaleDTO.getMerchantId()).orElseThrow(MerchantNotFoundException::new);
        Card card = cardRepository.findById(inputSaleDTO.getCardId()).orElseThrow(CardNotFoundException::new);
        return Sale.builder()
                .merchant(merchant)
                .client(client)
                .card(card)
                .originalPrice(inputSaleDTO.getPrice())
                .summaryPrice(inputSaleDTO.getPrice())
                .usedPoints(inputSaleDTO.getUsedPoints())
                .build();
    }

    public OutputSaleDTO mapOutput(Sale sale){
        return OutputSaleDTO.builder()
                .id(sale.getId())
                .originalPrice(sale.getOriginalPrice())
                .discountedPrice(sale.getDiscountedPrice())
                .summaryPrice(sale.getSummaryPrice())
                .usedPoints(sale.getUsedPoints())
                .cardBalance(cardRepository.findById(sale.getCard().getId()).orElseThrow().getBalance())
                .build();
    }
}
