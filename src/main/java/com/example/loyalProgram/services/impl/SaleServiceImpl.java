package com.example.loyalProgram.services.impl;


import com.example.loyalProgram.ClientModule.entities.Card;
import com.example.loyalProgram.ClientModule.entities.CardHistory;
import com.example.loyalProgram.ClientModule.entities.Client;
import com.example.loyalProgram.ClientModule.repositories.CardHistoryRepository;
import com.example.loyalProgram.ClientModule.repositories.CardRepository;
import com.example.loyalProgram.MerchantModule.entities.LoyalProgram;
import com.example.loyalProgram.MerchantModule.entities.Tier;
import com.example.loyalProgram.MerchantModule.repositories.LoyalProgramRepository;
import com.example.loyalProgram.MerchantModule.repositories.MerchantRepository;
import com.example.loyalProgram.MerchantModule.repositories.TierRepository;
import com.example.loyalProgram.SaleModule.DTOs.SaleDTO;
import com.example.loyalProgram.ClientModule.repositories.ClientRepository;
import com.example.loyalProgram.SaleModule.entities.Sale;
import com.example.loyalProgram.SaleModule.repositories.SaleRepository;
import com.example.loyalProgram.enums.PointStatus;
import com.example.loyalProgram.enums.TransactionStatus;
import com.example.loyalProgram.services.SaleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired ClientRepository clientRepository;
    @Autowired MerchantRepository merchantRepository;
    @Autowired SaleRepository saleRepository;
    @Autowired CardRepository cardRepository;
    @Autowired CardHistoryRepository cardHistoryRepository;
    @Autowired TierRepository tierRepository;
    @Autowired LoyalProgramRepository loyalProgramRepository;
    @Autowired ModelMapper modelMapper;

    @Override
    public void makeSale(SaleDTO saleDTO) {
        Sale sale = new Sale();
        Client currClient = clientRepository.findById(saleDTO.getClientId()).get();
        Tier currTier = tierRepository.findById(currClient.getTier().getId()).get();
        List<LoyalProgram> loyalPrograms = loyalProgramRepository.findAllByTier(currTier);
        loyalPrograms.sort(Comparator.comparing(LoyalProgram::getPriority));
        for (LoyalProgram loyalProgram:loyalPrograms){
            if(loyalProgram.getName().equals("Discount")) {
                sale.setClient(clientRepository.findById(saleDTO.getClientId()).get());
                sale.setMerchant(merchantRepository.findById(saleDTO.getMerchantId()).get());
                sale.setOriginalPrice(saleDTO.getPrice());
                BigDecimal discountedPrice = (saleDTO.getPrice().multiply(loyalProgram.getDiscountPercentage())).divide(BigDecimal.valueOf(100));
                sale.setDiscountedPrice(discountedPrice);
                sale.setSummaryPrice(saleDTO.getPrice().subtract(discountedPrice));
                saleRepository.save(sale);
            } else if (loyalProgram.getName().equals("AddBonusPoints")) {
                Card card = cardRepository.findById(currClient.getCard().getId()).get();
                BigDecimal currPoints = (sale.getSummaryPrice().multiply(loyalProgram.getDiscountPercentage())).divide(BigDecimal.valueOf(100));
                card.setBalance(card.getBalance().
                        add(currPoints));
                        cardRepository.save(card);
                        CardHistory cardHistory = new CardHistory();
                        LocalDateTime earnDate = LocalDateTime.now();
                        cardHistory.setEarnDate(earnDate);
                        cardHistory.setExpirationDate(earnDate.plusDays(10));
                        cardHistory.setPointStatus(PointStatus.AVAILABLE);
                        cardHistory.setPoints(currPoints);
                        cardHistory.setTransactionStatus(TransactionStatus.RECEIVED);
                        cardHistory.setCard(card);
                        cardHistoryRepository.save(cardHistory);
            }
        }
    }
}
