package com.example.loyalProgram.loyalPrograms.usePointsLoyalProgram;

import com.example.loyalProgram.clientModule.entities.Card;
import com.example.loyalProgram.clientModule.entities.CardHistory;
import com.example.loyalProgram.clientModule.repositories.CardHistoryRepository;
import com.example.loyalProgram.clientModule.repositories.CardRepository;
import com.example.loyalProgram.enums.PointStatus;
import com.example.loyalProgram.enums.TransactionStatus;
import com.example.loyalProgram.exceptionHandlingAndValidation.notFoundExceptions.CardNotFoundException;
import com.example.loyalProgram.loyalPrograms.baseLoyalProgram.LoyalProgramService;
import com.example.loyalProgram.saleModule.entities.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsePointsLoyalProgramService implements LoyalProgramService<UsePointsLoyalProgram> {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    CardHistoryRepository cardHistoryRepository;
    @Override
    public Sale applyProgram(Sale sale, UsePointsLoyalProgram loyalProgram) {
        Card card = cardRepository.findById(sale.getCard().getId()).orElseThrow(CardNotFoundException::new);
        updateStatusAndBalanceByDate(card);
        usePoints(card, sale.getUsedPoints(), sale);
        return sale;
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

    private void usePoints(Card card, BigDecimal usedPoints, Sale sale) {
        BigDecimal cardBalance = card.getBalance();
        if (cardBalance.compareTo(usedPoints) >= 0 && usedPoints.compareTo(BigDecimal.ZERO) != 0) {
            card.setBalance(cardBalance.subtract(usedPoints));
            sale.setSummaryPrice(sale.getSummaryPrice().subtract(usedPoints));
            sale.setDiscountedPrice(sale.getDiscountedPrice().add(usedPoints));
            generateRedeemPointsCardHistory(card, usedPoints);
            List<CardHistory> cardHistoryList = cardHistoryRepository.findAllByCardAndPointStatusOrderById(card, PointStatus.AVAILABLE);
            for (CardHistory cardHistory : cardHistoryList) {
                if (usedPoints.subtract(cardHistory.getAvailablePoints()).compareTo(BigDecimal.ZERO) > 0) {
                    usedPoints = usedPoints.subtract(cardHistory.getAvailablePoints());
                    cardHistory.setPointStatus(PointStatus.UNAVAILABLE);
                    setCurrentCardHistory(cardHistory, cardHistory.getReceivedPoints(), BigDecimal.ZERO);
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

    private void generateRedeemPointsCardHistory(Card card, BigDecimal usedPoints) {
        cardHistoryRepository.save(CardHistory.builder()
                .card(card)
                .usedPoints(usedPoints)
                .earnDate(LocalDateTime.now())
                .transactionStatus(TransactionStatus.USED)
                .build());
    }

    private void setCurrentCardHistory(CardHistory cardHistory, BigDecimal usedPoints, BigDecimal availablePoints) {
        cardHistory.setUsedPoints(usedPoints);
        cardHistory.setAvailablePoints(availablePoints);
        cardHistoryRepository.save(cardHistory);
    }
}
