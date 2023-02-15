package com.example.loyalProgram.ClientModule.repositories;

import com.example.loyalProgram.ClientModule.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
