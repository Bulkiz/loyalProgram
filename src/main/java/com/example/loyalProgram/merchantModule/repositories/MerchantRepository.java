package com.example.loyalProgram.merchantModule.repositories;

import com.example.loyalProgram.merchantModule.entities.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Integer> {
}
