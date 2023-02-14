package com.example.loyalProgram.repositories;

import com.example.loyalProgram.entities.Tier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TierRepository extends JpaRepository<Tier, Integer> {
}
