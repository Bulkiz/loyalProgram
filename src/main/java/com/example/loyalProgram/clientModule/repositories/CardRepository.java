package com.example.loyalProgram.clientModule.repositories;

import com.example.loyalProgram.clientModule.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
}
