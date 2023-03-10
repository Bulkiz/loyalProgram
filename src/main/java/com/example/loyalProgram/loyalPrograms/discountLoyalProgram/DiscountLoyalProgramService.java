package com.example.loyalProgram.loyalPrograms.discountLoyalProgram;

import com.example.loyalProgram.basePackage.Calculator;
import com.example.loyalProgram.basePackage.GenerateCard;
import com.example.loyalProgram.loyalPrograms.baseLoyalProgram.LoyalProgramService;
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
    @Autowired private SaleRepository saleRepository;

    @Autowired private GenerateCard generateCard;
    private BigDecimal currDiscountPrice;
    @Override
    public Sale applyProgram(Sale currSale, DiscountLoyalProgram discountLoyalProgram) {
        Sale sale = discountSaleMethod(currSale, discountLoyalProgram.getDiscountPercentage());
        generateCard.generateSaleBonus(sale, discountLoyalProgram);
        return sale;
    }

    private Sale discountSaleMethod(Sale sale, BigDecimal discountPercentage) {
        BigDecimal discountedPrice = Calculator.calculatePercentage(sale.getOriginalPrice(), discountPercentage);
        currDiscountPrice = discountedPrice;
        if(sale.getDiscountedPrice() != null) {
            currDiscountPrice =  Calculator.calculatePercentage(sale.getSummaryPrice(), discountPercentage);
            discountedPrice = sale.getDiscountedPrice().add(currDiscountPrice );
        }
        sale.setDiscountedPrice(discountedPrice);
        sale.setSummaryPrice(sale.getOriginalPrice().subtract(discountedPrice));
        return saleRepository.save(sale);
    }

}
