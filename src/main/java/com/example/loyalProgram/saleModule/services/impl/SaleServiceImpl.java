package com.example.loyalProgram.saleModule.services.impl;


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
import com.example.loyalProgram.saleModule.services.SaleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.MonthDay;
import java.util.Comparator;
import java.util.List;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired private ClientRepository clientRepository;
    @Autowired private MerchantRepository merchantRepository;
    @Autowired private SaleRepository saleRepository;
    @Autowired private CardRepository cardRepository;
    @Autowired private CardHistoryRepository cardHistoryRepository;
    @Autowired private TierRepository tierRepository;
    @Autowired private LoyalProgramRepository loyalProgramRepository;
    @Autowired private SaleBonusRepository saleBonusRepository;

    @Override
    @Transactional
    public BigDecimal makeSale(Sale currSale) {
        Sale sale = new Sale();
        List<LoyalProgram> loyalPrograms = getLoyalProgramsSorted(currSale);
        BigDecimal birthdayDiscountPercentage = BigDecimal.ZERO;
        for (LoyalProgram loyalProgram : loyalPrograms) {
            switch (loyalProgram.getType()) {
                case BIRTHDAY -> {
                    if (checkBirthday(currSale)) {
                        birthdayDiscountPercentage = birthdayDiscountPercentage.add(loyalProgram.getDiscountPercentage());
                    }
                }
                case DISCOUNT -> {
                    sale = discountSaleMethod(currSale, loyalProgram.getDiscountPercentage().add(birthdayDiscountPercentage));
                    saleBonusRepository.save(generateSaleBonus(sale, loyalProgram));
                }
                case USE_POINTS -> {
                    Card card = getCurrClient(currSale).getCard();
                    updateStatusAndBalanceByDate(card);
                    redeemPoints(card, currSale.getUsedPoints(), sale);
                }
                case ADD_POINTS -> cardTransaction(getCurrClient(currSale), sale, loyalProgram.getDiscountPercentage());
                default -> throw new IllegalArgumentException();
            }
        }
        updateAmountAndCheckTier(currSale.getClient(), currSale.getSummaryPrice());
        return sale.getDiscountedPrice();
    }

    private void updateAmountAndCheckTier(Client client, BigDecimal summaryPrice) {
        client.setAmountSpend(client.getAmountSpend().add(summaryPrice));
        if (client.getTier().getTierAmount().compareTo(client.getAmountSpend()) >= 0){
            client.setTier(tierRepository.
                    findFirstByMerchantAndTierAmountGreaterThanOrderByTierAmount(client.getMerchant(),
                            client.getTier().getTierAmount()));
        }
        clientRepository.save(client);
    }

    private boolean checkBirthday(Sale currSale) {
        MonthDay birthday = MonthDay.from(getCurrClient(currSale).getBirthday());
        MonthDay today = MonthDay.now();
        return birthday.equals(today);
    }

    private SaleBonus generateSaleBonus(Sale sale, LoyalProgram loyalProgram) {
       return SaleBonus.builder()
                .loyalProgram(loyalProgram)
                .sale(sale)
                .currentPrice(sale.getSummaryPrice())
                .savedMoney(sale.getDiscountedPrice())
                .build();
    }

    private Sale discountSaleMethod(Sale sale, BigDecimal discountPercentage) {
        sale.setClient(clientRepository.findById(sale.getClient().getId()).orElseThrow());
        sale.setMerchant(merchantRepository.findById(sale.getMerchant().getId()).orElseThrow());
        sale.setOriginalPrice(sale.getOriginalPrice());
        BigDecimal discountedPrice = calculatePercentage(sale.getOriginalPrice(), discountPercentage);
        sale.setDiscountedPrice(discountedPrice);
        sale.setSummaryPrice(sale.getOriginalPrice().subtract(discountedPrice));
        saleRepository.save(sale);
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
        LocalDateTime earnDate = LocalDateTime.now();
        cardHistoryRepository.save(CardHistory.builder().
                earnDate(earnDate)
                .expirationDate(earnDate.plusSeconds(9))
                .pointStatus(PointStatus.AVAILABLE)
                .receivedPoints(currPoints)
                .usedPoints(BigDecimal.ZERO)
                .availablePoints(currPoints)
                .transactionStatus(TransactionStatus.RECEIVED)
                .card(card)
                .build());
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
            sale.setDiscountedPrice(sale.getDiscountedPrice().add(usedPoints));
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
        cardHistoryRepository.save(CardHistory.builder()
                .card(card)
                .receivedPoints(usedPoints)
                .earnDate(LocalDateTime.now())
                .transactionStatus(TransactionStatus.USED)
                .build());
    }
}
