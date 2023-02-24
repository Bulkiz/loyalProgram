package com.example.loyalProgram.merchantModule.repositories;

import com.example.loyalProgram.merchantModule.entities.Merchant;
import com.example.loyalProgram.merchantModule.entities.Tier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TierRepository extends JpaRepository<Tier, Integer> {
    Tier findFirstByMerchantAndTierAmountGreaterThanOrderByTierAmount(Merchant merchant, BigDecimal tierAmount);
}
