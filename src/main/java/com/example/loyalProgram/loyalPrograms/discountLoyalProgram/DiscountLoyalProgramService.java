package com.example.loyalProgram.loyalPrograms.discountLoyalProgram;

import com.example.loyalProgram.basePackage.Calculator;
import com.example.loyalProgram.basePackage.GenerateCardAndSaleBonus;
import com.example.loyalProgram.loyalPrograms.baseLoyalProgram.LoyalProgramService;
import com.example.loyalProgram.saleModule.entities.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DiscountLoyalProgramService implements LoyalProgramService<DiscountLoyalProgram> {
    @Autowired private GenerateCardAndSaleBonus generateCardAndSaleBonus;

    @Override
    public Sale applyProgram(Sale currSale, DiscountLoyalProgram discountLoyalProgram) {
        Sale sale = discountSaleMethod(currSale, discountLoyalProgram.getDiscountPercentage());
        generateCardAndSaleBonus.generateSaleBonus(sale, discountLoyalProgram, sale.getDiscountedPrice());
        return sale;
    }

    private Sale discountSaleMethod(Sale sale, BigDecimal discountPercentage) {
        BigDecimal discountedPrice = Calculator.calculatePercentage(sale.getOriginalPrice(), discountPercentage);
        BigDecimal currDiscountPrice;
        if(sale.getDiscountedPrice() != null) {
            currDiscountPrice =  Calculator.calculatePercentage(sale.getSummaryPrice(), discountPercentage);
            discountedPrice = sale.getDiscountedPrice().add(currDiscountPrice);
        }
        sale.setDiscountedPrice(discountedPrice);
        sale.setSummaryPrice(sale.getOriginalPrice().subtract(discountedPrice));
        return sale;
    }

}
