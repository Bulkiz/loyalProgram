package com.example.loyalProgram.merchantModule.repositories;

import com.example.loyalProgram.merchantModule.entities.LoyalProgram;
import com.example.loyalProgram.merchantModule.entities.Tier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoyalProgramRepository extends JpaRepository<LoyalProgram, Integer> {
    List<LoyalProgram> findAllByTier(Tier tier);
}
