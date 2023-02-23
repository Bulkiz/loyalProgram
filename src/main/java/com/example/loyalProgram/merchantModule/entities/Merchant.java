package com.example.loyalProgram.merchantModule.entities;

import com.example.loyalProgram.clientModule.entities.Client;
import com.example.loyalProgram.baseEntity.BaseEntity;
import com.example.loyalProgram.saleModule.entities.Sale;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Merchant extends BaseEntity {
    private String name;
    @OneToMany(mappedBy = "merchant")
    private List<Sale> sales;
    @OneToMany(mappedBy = "merchant")
    private List<Tier> tiers;
    @OneToMany(mappedBy = "merchant")
    private List<Client> clients;

}
