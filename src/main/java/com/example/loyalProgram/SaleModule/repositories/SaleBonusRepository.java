package com.example.loyalProgram.SaleModule.repositories;

import com.example.loyalProgram.SaleModule.entities.SaleBonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleBonusRepository extends JpaRepository<SaleBonus, Integer> {
}
