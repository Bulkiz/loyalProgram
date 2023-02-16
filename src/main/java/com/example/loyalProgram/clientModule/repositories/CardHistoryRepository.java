package com.example.loyalProgram.clientModule.repositories;

import com.example.loyalProgram.clientModule.entities.CardHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardHistoryRepository extends JpaRepository<CardHistory, Integer> {
}
