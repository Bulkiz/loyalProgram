package com.example.loyalProgram.clientModule.repositories;

import com.example.loyalProgram.clientModule.entities.Card;
import com.example.loyalProgram.clientModule.entities.CardHistory;
import com.example.loyalProgram.enums.PointStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardHistoryRepository extends JpaRepository<CardHistory, Integer> {
    List<CardHistory> findAllByCardAndPointStatusOrderById(Card card, PointStatus pointStatus);
    CardHistory findFirstByCard(Card card);
}
