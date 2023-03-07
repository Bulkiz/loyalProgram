package com.example.loyalProgram.loyalPrograms.addPointsLoyalProgram;

import com.example.loyalProgram.loyalPrograms.Calculator;
import com.example.loyalProgram.clientModule.entities.Card;
import com.example.loyalProgram.clientModule.entities.CardHistory;
import com.example.loyalProgram.clientModule.repositories.CardHistoryRepository;
import com.example.loyalProgram.clientModule.repositories.CardRepository;
import com.example.loyalProgram.enums.PointStatus;
import com.example.loyalProgram.enums.TransactionStatus;
import com.example.loyalProgram.saleModule.entities.Sale;
import com.example.loyalProgram.loyalPrograms.baseLoyalProgram.LoyalProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class AddPointsLoyalProgramService implements LoyalProgramService<AddPointsLoyalProgram> {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardHistoryRepository cardHistoryRepository;

    @Override
    public Sale applyProgram(Sale sale, AddPointsLoyalProgram loyalProgram) {
        cardTransaction(sale, sale.getDiscountedPrice());
        return sale;
    }

    private void cardTransaction(Sale sale , BigDecimal discountPercentage) {
        Card card = cardRepository.findById(sale.getCard().getId()).orElseThrow();
        BigDecimal currPoints = Calculator.calculatePercentage(sale.getSummaryPrice(), discountPercentage);
        card.setBalance(card.getBalance().add(currPoints));
        cardRepository.save(card);
        generateCardHistory(card, currPoints);
    }

    private void generateCardHistory(Card card, BigDecimal currPoints) {
        LocalDateTime earnDate = LocalDateTime.now();
        cardHistoryRepository.save(CardHistory.builder().
                earnDate(earnDate)
                .expirationDate(earnDate.plusSeconds(9000000))
                .pointStatus(PointStatus.AVAILABLE)
                .receivedPoints(currPoints)
                .usedPoints(BigDecimal.ZERO)
                .expiredPoints(BigDecimal.ZERO)
                .availablePoints(currPoints)
                .transactionStatus(TransactionStatus.RECEIVED)
                .card(card)
                .build());
    }
}
