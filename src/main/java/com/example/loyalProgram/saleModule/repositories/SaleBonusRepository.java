package com.example.loyalProgram.saleModule.repositories;

import com.example.loyalProgram.saleModule.entities.SaleBonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleBonusRepository extends JpaRepository<SaleBonus, Integer> {
}
