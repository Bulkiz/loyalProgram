package com.example.loyalProgram.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tier extends BaseEntity{

    private String name;
    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;
}
