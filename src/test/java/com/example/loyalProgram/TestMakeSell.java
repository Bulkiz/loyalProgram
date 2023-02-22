package com.example.loyalProgram;

import com.example.loyalProgram.clientModule.repositories.ClientRepository;
import com.example.loyalProgram.merchantModule.repositories.MerchantRepository;
import com.example.loyalProgram.saleModule.entities.Sale;
import com.example.loyalProgram.saleModule.services.SaleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.mockito.Mockito.mock;


@SpringBootTest
public class TestMakeSell {
    @Autowired SaleService saleService;
    @Autowired ClientRepository clientRepository;
    @Autowired  MerchantRepository merchantRepository ;
    Sale testSale = mock(Sale.class);

    @BeforeEach
    public void setUp() {
        testSale = Sale.builder()
                .client(clientRepository.findById(2).get())
                .merchant(merchantRepository.findById(1).get())
                .originalPrice(BigDecimal.valueOf(100))
                .usedPoints(BigDecimal.ZERO).build();
    }

    @Test
    public void testMakeSell() {
      //  when(saleService.makeSale(testSale)).thenReturn(BigDecimal.valueOf(100));
        Assertions.assertEquals(saleService.makeSale(testSale),BigDecimal.valueOf(30).setScale(2));
    }
}
