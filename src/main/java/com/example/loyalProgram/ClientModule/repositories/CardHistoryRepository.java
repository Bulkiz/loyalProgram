package com.example.loyalProgram.ClientModule.repositories;

import com.example.loyalProgram.ClientModule.entities.CardHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardHistoryRepository extends JpaRepository<CardHistory, Integer> {
}
