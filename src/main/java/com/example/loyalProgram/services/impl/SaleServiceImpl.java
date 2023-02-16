package com.example.loyalProgram.services.impl;


import com.example.loyalProgram.clientModule.entities.Card;
import com.example.loyalProgram.clientModule.entities.CardHistory;
import com.example.loyalProgram.clientModule.entities.Client;
import com.example.loyalProgram.clientModule.repositories.CardHistoryRepository;
import com.example.loyalProgram.clientModule.repositories.CardRepository;
import com.example.loyalProgram.clientModule.repositories.ClientRepository;
import com.example.loyalProgram.enums.PointStatus;
import com.example.loyalProgram.enums.TransactionStatus;
import com.example.loyalProgram.merchantModule.entities.LoyalProgram;
import com.example.loyalProgram.merchantModule.entities.Tier;
import com.example.loyalProgram.merchantModule.repositories.LoyalProgramRepository;
import com.example.loyalProgram.merchantModule.repositories.MerchantRepository;
import com.example.loyalProgram.merchantModule.repositories.TierRepository;
import com.example.loyalProgram.saleModule.entities.Sale;
import com.example.loyalProgram.saleModule.repositories.SaleRepository;
import com.example.loyalProgram.services.SaleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public void makeSale(Sale currSale) {
        Sale sale = new Sale();
        List<LoyalProgram> loyalPrograms = getLoyalProgramsSorted(currSale);
        for (LoyalProgram loyalProgram : loyalPrograms) {

            BigDecimal discountPercentage = loyalProgram.getDiscountPercentage();

            switch (loyalProgram.getType()) {
                case DISCOUNT -> {
                    sale = discountSaleMethod(currSale, discountPercentage);
                    saleRepository.save(sale);
                }
                case ADD_POINTS -> cardTransaction(getCurrClient(currSale), sale, discountPercentage);
                default -> {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    private Sale discountSaleMethod(Sale sale, BigDecimal discountPercentage) {
        Sale currSale = new Sale();
        currSale.setClient(clientRepository.findById(sale.getClient().getId()).orElseThrow());
        currSale.setMerchant(merchantRepository.findById(sale.getMerchant().getId()).orElseThrow());
        currSale.setOriginalPrice(sale.getOriginalPrice());
        BigDecimal discountedPrice = calculatePercentage(sale.getOriginalPrice(), discountPercentage);
        currSale.setDiscountedPrice(discountedPrice);
        currSale.setSummaryPrice(sale.getOriginalPrice().subtract(discountedPrice));
        return currSale;
    }

    private void cardTransaction(Client currClient, Sale sale, BigDecimal discountPercentage) {
        Card card = cardRepository.findById(currClient.getCard().getId()).orElseThrow();
        BigDecimal currPoints = calculatePercentage(sale.getSummaryPrice(), discountPercentage);
        card.setBalance(card.getBalance().add(currPoints));
        cardRepository.save(card);
        generateCardHistory(card, currPoints);
    }

    private void generateCardHistory(Card card, BigDecimal currPoints) {
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

    private List<LoyalProgram> getLoyalProgramsSorted(Sale sale) {
        Client currClient = getCurrClient(sale);
        Tier currTier = tierRepository.findById(currClient.getTier().getId()).orElseThrow();
        List<LoyalProgram> loyalPrograms = loyalProgramRepository.findAllByTier(currTier);
        loyalPrograms.sort(Comparator.comparing(LoyalProgram::getPriority));
        return loyalPrograms;
    }

    private Client getCurrClient(Sale sale) {
        return clientRepository.findById(sale.getClient().getId()).orElseThrow();
    }

    private BigDecimal calculatePercentage(BigDecimal price, BigDecimal discountPercentage) {
        return (price.multiply(discountPercentage)).divide(BigDecimal.valueOf(100), RoundingMode.CEILING);
    }
}
