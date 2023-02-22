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
import com.example.loyalProgram.saleModule.entities.SaleBonus;
import com.example.loyalProgram.saleModule.repositories.SaleBonusRepository;
import com.example.loyalProgram.saleModule.repositories.SaleRepository;
import com.example.loyalProgram.services.SaleService;
import jakarta.transaction.Transactional;
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
    @Autowired SaleBonusRepository saleBonusRepository;
    @Autowired ModelMapper modelMapper;

    @Override
    @Transactional
    public void makeSale(Sale currSale) {
        Sale sale = new Sale();
        List<LoyalProgram> loyalPrograms = getLoyalProgramsSorted(currSale);
        for (LoyalProgram loyalProgram : loyalPrograms) {

            BigDecimal discountPercentage = loyalProgram.getDiscountPercentage();

            switch (loyalProgram.getType()) {
                case DISCOUNT -> {
                    sale = discountSaleMethod(currSale, discountPercentage);
                    saleRepository.save(sale);
                    saleBonusRepository.save(generateSaleBonus(sale, loyalProgram));
                }
                case USE_POINTS -> {
                    Card card = getCurrClient(currSale).getCard();
                    updateStatusAndBalanceByDate(card);
                    redeemPoints(card, currSale.getUsedPoints(), sale);
                }
                case ADD_POINTS -> cardTransaction(getCurrClient(currSale), sale, discountPercentage);

                default -> System.out.println("!!"); //throw new IllegalArgumentException();

            }
        }
    }

    private SaleBonus generateSaleBonus(Sale sale, LoyalProgram loyalProgram) {
        SaleBonus saleBonus = new SaleBonus();
        saleBonus.setLoyalProgram(loyalProgram);
        saleBonus.setSale(sale);
        saleBonus.setCurrentPrice(sale.getSummaryPrice());
        saleBonus.setSavedMoved(sale.getDiscountedPrice());
        return saleBonus;
    }

    private Sale discountSaleMethod(Sale sale, BigDecimal discountPercentage) {
        sale.setClient(clientRepository.findById(sale.getClient().getId()).orElseThrow());
        sale.setMerchant(merchantRepository.findById(sale.getMerchant().getId()).orElseThrow());
        sale.setOriginalPrice(sale.getOriginalPrice());
        BigDecimal discountedPrice = calculatePercentage(sale.getOriginalPrice(), discountPercentage);
        sale.setDiscountedPrice(discountedPrice);
        sale.setSummaryPrice(sale.getOriginalPrice().subtract(discountedPrice));
        return sale;
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
        cardHistory.setReceivedPoints(currPoints);
        cardHistory.setAvailablePoints(currPoints);
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
        return price.multiply(discountPercentage).divide(BigDecimal.valueOf(100), RoundingMode.FLOOR);
    }

    private void updateStatusAndBalanceByDate(Card card) {
        LocalDateTime currentDate = LocalDateTime.now();
        List<CardHistory> cardHistoryList = cardHistoryRepository.findAllByCardAndPointStatusOrderById(card, PointStatus.AVAILABLE);
        cardHistoryList.forEach(cardHistory -> {
            if (currentDate.isAfter(cardHistory.getExpirationDate()) && cardHistory.getPointStatus() == PointStatus.AVAILABLE) {
                cardHistory.setPointStatus(PointStatus.EXPIRED);
                cardHistory.setExpiredPoints(cardHistory.getAvailablePoints());
                cardHistory.setAvailablePoints(BigDecimal.ZERO);
                cardHistoryRepository.save(cardHistory);
                card.setBalance(card.getBalance().subtract(cardHistory.getReceivedPoints()));
                cardRepository.save(card);
            }
        });
    }

    private void redeemPoints(Card card, BigDecimal usedPoints, Sale sale) {
        BigDecimal cardBalance = card.getBalance();

        if (cardBalance.compareTo(usedPoints) >= 0 && usedPoints.compareTo(BigDecimal.ZERO) != 0) {

            card.setBalance(cardBalance.subtract(usedPoints));
            sale.setSummaryPrice(sale.getSummaryPrice().subtract(usedPoints));
            generateRedeemPointsCardHistory(card, usedPoints);

            List<CardHistory> cardHistoryList = cardHistoryRepository.findAllByCardAndPointStatusOrderById(card, PointStatus.AVAILABLE);

            for (CardHistory cardHistory : cardHistoryList) {
                if (usedPoints.subtract(cardHistory.getAvailablePoints()).compareTo(BigDecimal.ZERO) > 0) {
                    cardHistory.setPointStatus(PointStatus.UNAVAILABLE);
                    setCurrentCardHistory(cardHistory, cardHistory.getReceivedPoints(), BigDecimal.ZERO);
                    usedPoints = usedPoints.subtract(cardHistory.getAvailablePoints());
                } else if (usedPoints.subtract(cardHistory.getAvailablePoints()).compareTo(BigDecimal.ZERO) < 0) {
                    setCurrentCardHistory(cardHistory, cardHistory.getUsedPoints().add(usedPoints), cardHistory.getAvailablePoints().subtract(usedPoints));
                    break;
                } else {
                    setCurrentCardHistory(cardHistory, cardHistory.getReceivedPoints(), BigDecimal.ZERO);
                    break;
                }
            }
        }
    }

    private void setCurrentCardHistory(CardHistory cardHistory, BigDecimal usedPoints, BigDecimal availablePoints) {
        cardHistory.setUsedPoints(usedPoints);
        cardHistory.setAvailablePoints(availablePoints);
        cardHistoryRepository.save(cardHistory);
    }

    private void generateRedeemPointsCardHistory(Card card, BigDecimal usedPoints) {
        CardHistory newCardHistory = new CardHistory();
        newCardHistory.setCard(card);
        newCardHistory.setReceivedPoints(usedPoints);
        newCardHistory.setEarnDate(LocalDateTime.now());
        newCardHistory.setTransactionStatus(TransactionStatus.USED);
        cardHistoryRepository.save(newCardHistory);
    }
}
