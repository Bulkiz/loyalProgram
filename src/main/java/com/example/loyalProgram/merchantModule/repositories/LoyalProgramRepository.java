package com.example.loyalProgram.merchantModule.repositories;

import com.example.loyalProgram.loyalPrograms.baseLoyalProgram.LoyalProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoyalProgramRepository extends JpaRepository<LoyalProgram, Integer> {
    @Query(nativeQuery = true, value = "select * from loyal_program lp where lp .tier_id = :tier_id order by priority asc")
    List<LoyalProgram> findAllByTier(Integer tier_id);

}
