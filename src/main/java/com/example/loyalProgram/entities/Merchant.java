package com.example.loyalProgram.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
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
