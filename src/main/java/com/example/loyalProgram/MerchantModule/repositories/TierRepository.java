package com.example.loyalProgram.MerchantModule.repositories;

import com.example.loyalProgram.MerchantModule.entities.Tier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TierRepository extends JpaRepository<Tier, Integer> {
}
