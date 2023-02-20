package com.example.loyalProgram.services.impl;

import com.example.loyalProgram.clientModule.entities.CardHistory;
import com.example.loyalProgram.clientModule.repositories.CardHistoryRepository;
import com.example.loyalProgram.clientModule.repositories.CardRepository;
import com.example.loyalProgram.enums.PointStatus;
import com.example.loyalProgram.services.RedeemPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RedeemPointsServiceImpl implements RedeemPointsService {

    @Autowired CardHistoryRepository cardHistoryRepository;
    @Autowired CardRepository cardRepository;
    @Override
    public void updateStatusByDate() {
//        LocalDateTime currentDate = LocalDateTime.now();
//        List<CardHistory> cardHistoryList = cardHistoryRepository.findAll();
//        for(CardHistory cardHistory:cardHistoryList) {
//            LocalDateTime expirationDate = cardHistory.getExpirationDate();
//            if(currentDate.isAfter(expirationDate) && cardHistory.getPointStatus().equals(PointStatus.AVAILABLE)){
//                cardHistory.setPointStatus(PointStatus.EXPIRED);
//            }
//            cardHistoryRepository.save(cardHistory);
//        }
    }


}
