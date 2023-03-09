package com.example.loyalProgram;

import com.example.loyalProgram.clientModule.entities.Client;
import com.example.loyalProgram.clientModule.repositories.CardRepository;
import com.example.loyalProgram.loyalPrograms.baseLoyalProgram.LoyalProgram;
import com.example.loyalProgram.loyalPrograms.discountLoyalProgram.DiscountLoyalProgram;
import com.example.loyalProgram.merchantModule.entities.Merchant;
import com.example.loyalProgram.merchantModule.entities.Tier;
import com.example.loyalProgram.merchantModule.services.impl.MerchantServiceImpl;
import com.example.loyalProgram.saleModule.entities.Sale;
import com.example.loyalProgram.saleModule.repositories.SaleRepository;
import com.example.loyalProgram.saleModule.services.SaleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.mock;


@SpringBootTest
@TestPropertySource(locations = "classpath:application.yml")
public class TestDiscountLoyalProgram {
    @Autowired
    SaleService saleService;
    @Autowired
    MerchantServiceImpl addingService;
    Merchant testMerchant = mock(Merchant.class);
    Sale testSale = mock(Sale.class);
    Client testClient = mock(Client.class);
    LoyalProgram testLoyalProgram = mock(DiscountLoyalProgram.class);
    Tier testTier = mock(Tier.class);
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private CardRepository cardRepository;

    @BeforeEach
    public void setUp() {
        testMerchant = Merchant.builder().name("TestMerchant").build();
        addingService.addMerchant(testMerchant);

        testLoyalProgram = DiscountLoyalProgram.builder()
                .priority(10)
                .discountPercentage(BigDecimal.TEN)
                .build();

        List<LoyalProgram> testListLoyalProgram = new LinkedList<>();
        testListLoyalProgram.add(testLoyalProgram);

        testTier = Tier.builder()
                .name("TestTier")
                .merchant(testMerchant)
                .loyalPrograms(testListLoyalProgram)
                .tierAmount(BigDecimal.TEN)
                .build();

        List<Tier> testListTier = new LinkedList<>();
        testListTier.add(testTier);

        addingService.addTiers(testMerchant.getId(), testListTier);

        testClient = Client.builder()
                .name("TestClient")
                .tier(testTier)
                .cards(new ArrayList<>())
                .birthday(LocalDate.now())
                .amountSpend(BigDecimal.ZERO)
                .build();

        List<Client> testListClients = new LinkedList<>();
        testListClients.add(testClient);

        addingService.addClients(testMerchant.getId(), testListClients);

        testSale = Sale.builder()
                .client(testClient)
                .merchant(testMerchant)
                .card(cardRepository.findById(testClient.getCards().get(0).getId()).orElseThrow())
                .originalPrice(BigDecimal.valueOf(100))
                .usedPoints(BigDecimal.ZERO)
                .build();
    }

    @Test
    public void testMakeSell() {
        Assertions.assertEquals(saleService.makeSale(testSale).getDiscountedPrice(), BigDecimal.valueOf(10).setScale(2, RoundingMode.FLOOR));
        Assertions.assertEquals(saleRepository.findById(testSale.getId()).orElseThrow().getId(), testSale.getId());
    }
}

