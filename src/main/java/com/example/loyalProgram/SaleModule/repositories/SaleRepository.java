package com.example.loyalProgram.SaleModule.repositories;

import com.example.loyalProgram.SaleModule.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {
}
