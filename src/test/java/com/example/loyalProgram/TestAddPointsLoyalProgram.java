package com.example.loyalProgram;

import com.example.loyalProgram.clientModule.entities.Card;
import com.example.loyalProgram.clientModule.entities.Client;
import com.example.loyalProgram.clientModule.repositories.CardRepository;
import com.example.loyalProgram.loyalPrograms.addPointsLoyalProgram.AddPointsLoyalProgram;
import com.example.loyalProgram.loyalPrograms.baseLoyalProgram.LoyalProgram;
import com.example.loyalProgram.merchantModule.entities.Merchant;
import com.example.loyalProgram.merchantModule.entities.Tier;
import com.example.loyalProgram.merchantModule.services.impl.AddingServiceImpl;
import com.example.loyalProgram.saleModule.entities.Sale;
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
public class TestAddPointsLoyalProgram {
    @Autowired
    SaleService saleService;
    @Autowired
    AddingServiceImpl addingService;
    Merchant testMerchant = mock(Merchant.class);
    Sale testSale = mock(Sale.class);
    Client testClient = mock(Client.class);
    LoyalProgram testLoyalProgram = mock(AddPointsLoyalProgram.class);
    Tier testTier = mock(Tier.class);

    @Autowired
    CardRepository cardRepository;

    @BeforeEach
    public void setUp() {
        testMerchant = Merchant.builder().name("TestMerchant").build();
        addingService.addMerchant(testMerchant);

        testLoyalProgram = AddPointsLoyalProgram.builder()
                .priority(30)
                .scale(BigDecimal.valueOf(1.5))
                .discountPercentage(BigDecimal.valueOf(20))
                .build();

        List<LoyalProgram> testListLoyalProgram = new LinkedList<>();
        testListLoyalProgram.add(testLoyalProgram);

        testTier = Tier.builder()
                .name("TestTier")
                .merchant(testMerchant)
                .loyalPrograms(testListLoyalProgram)
                .tierAmount(BigDecimal.valueOf(100))
                .build();

        List<Tier> testListTier = new LinkedList<>();
        testListTier.add(testTier);

        addingService.addTiers(testMerchant.getId(), testListTier);

        testClient = Client.builder()
                .name("TestClient")
                .merchant(testMerchant)
                .tier(testTier)
                .cards(new ArrayList<>())
                .birthday(LocalDate.now())
                .amountSpend(BigDecimal.ZERO)
                .build();

        List<Client> testListClients = new LinkedList<>();
        testListClients.add(testClient);

        addingService.addClients(testListClients);

        testSale = Sale.builder()
                .client(testClient)
                .merchant(testMerchant)
                .summaryPrice(BigDecimal.valueOf(90))
                .discountedPrice(BigDecimal.valueOf(10))
                .card(cardRepository.findById(testClient.getCards().get(0).getId()).orElseThrow())
                .originalPrice(BigDecimal.valueOf(100))
                .build();
    }

    @Test
    public void testMakeSell() {
        Card testCard= testClient.getCards().get(0);
        BigDecimal testCardBalance = testCard.getBalance();
        saleService.makeSale(testSale);
        Assertions.assertEquals(cardRepository.findById(testCard.getId()).orElseThrow().getBalance(),
                testCardBalance.add(BigDecimal.valueOf(18)).setScale(2, RoundingMode.FLOOR));

    }
}


