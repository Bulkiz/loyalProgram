package com.example.loyalProgram.MerchantModule.repositories;

import com.example.loyalProgram.MerchantModule.entities.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Integer> {
}
