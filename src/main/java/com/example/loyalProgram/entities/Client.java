package com.example.loyalProgram.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client extends BaseEntity{

    private String name;
    @OneToMany(mappedBy = "client")
    private List<Sale> sales;
    @OneToMany(mappedBy = "client")
    private List<Card> cards;

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;
}
