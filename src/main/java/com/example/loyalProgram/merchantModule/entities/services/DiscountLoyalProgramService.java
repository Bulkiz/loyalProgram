package com.example.loyalProgram.merchantModule.entities.services;

import com.example.loyalProgram.Calculator;
import com.example.loyalProgram.clientModule.repositories.ClientRepository;
import com.example.loyalProgram.merchantModule.entities.loyals.DiscountLoyalProgram;
import com.example.loyalProgram.merchantModule.repositories.MerchantRepository;
import com.example.loyalProgram.saleModule.entities.Sale;
import com.example.loyalProgram.saleModule.entities.SaleBonus;
import com.example.loyalProgram.saleModule.repositories.SaleBonusRepository;
import com.example.loyalProgram.saleModule.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DiscountLoyalProgramService implements LoyalProgramService<DiscountLoyalProgram> {
    @Autowired private SaleBonusRepository saleBonusRepository;
    @Autowired private ClientRepository clientRepository;
    @Autowired private MerchantRepository merchantRepository;
    @Autowired private SaleRepository saleRepository;
    @Override
    public Sale applyProgram(Sale currSale, DiscountLoyalProgram discountLoyalProgram) {
        Sale sale;
        sale = discountSaleMethod(currSale, discountLoyalProgram.getDiscountPercentage());
        saleBonusRepository.save(generateSaleBonus(currSale, discountLoyalProgram));
        return sale;
    }

    private Sale discountSaleMethod(Sale sale, BigDecimal discountPercentage) {
        sale.setClient(clientRepository.findById(sale.getClient().getId()).orElseThrow());
        sale.setMerchant(merchantRepository.findById(sale.getMerchant().getId()).orElseThrow());
        sale.setOriginalPrice(sale.getOriginalPrice());
        BigDecimal discountedPrice = Calculator.calculatePercentage(sale.getOriginalPrice(), discountPercentage);
        sale.setDiscountedPrice(discountedPrice);
        sale.setSummaryPrice(sale.getOriginalPrice().subtract(discountedPrice));
        saleRepository.save(sale);
        return sale;
    }
    private SaleBonus generateSaleBonus(Sale sale, DiscountLoyalProgram discountLoyalProgram) {
        return SaleBonus.builder()
                .loyalProgram(discountLoyalProgram)
                .sale(sale)
                .currentPrice(sale.getSummaryPrice())
                .savedMoney(sale.getDiscountedPrice())
                .build();
    }
}
