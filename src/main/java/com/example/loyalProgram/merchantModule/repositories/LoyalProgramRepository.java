package com.example.loyalProgram.merchantModule.repositories;

import com.example.loyalProgram.merchantModule.entities.loyals.LoyalProgram;
import com.example.loyalProgram.merchantModule.entities.Tier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoyalProgramRepository extends JpaRepository<LoyalProgram, Integer> {
    @Query(nativeQuery = true, value = "select * from loyal_program lp where lp .tier_id = 1 order by priority desc")
    List<LoyalProgram> findAllByTier(Tier tier);
}
