package com.example.loyalProgram.repositories;

import com.example.loyalProgram.entities.LoyalProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoyalProgramRepository extends JpaRepository<LoyalProgram, Integer> {
}
