package com.example.loyalProgram;

import com.example.loyalProgram.clientModule.entities.Client;
import com.example.loyalProgram.clientModule.repositories.CardRepository;
import com.example.loyalProgram.clientModule.repositories.ClientRepository;
import com.example.loyalProgram.enums.LoyalProgramType;
import com.example.loyalProgram.merchantModule.entities.loyals.LoyalProgram;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.mock;

@SpringBootTest
public class TestMakeSaleUpgradeClientTier {
    @Autowired
    SaleService saleService;
    @Autowired
    AddingServiceImpl addingService;

    @Autowired
    ClientRepository clientRepository;
    Merchant testMerchant = mock(Merchant.class);
    Sale testSale = mock(Sale.class);
    Client testClient = mock(Client.class);
    LoyalProgram testLoyalProgram = mock(LoyalProgram.class);
    LoyalProgram testLoyalProgramDiscount = mock(LoyalProgram.class);
    Tier firstTestTier = mock(Tier.class);
    Tier secondTier = mock(Tier.class);

    @Autowired
    CardRepository cardRepository;

    @BeforeEach
    public void setUp() {
        testMerchant = Merchant.builder().name("TestMerchant").build();
        addingService.addMerchant(testMerchant);

        testLoyalProgramDiscount = LoyalProgram.builder()
                .name("TestLoyalProgram")
                .priority(10)
                .discountPercentage(BigDecimal.TEN)
                .type(LoyalProgramType.DISCOUNT)
                .build();


        testLoyalProgram = LoyalProgram.builder()
                .name("TestLoyalProgram")
                .priority(30)
                .discountPercentage(BigDecimal.TEN)
                .type(LoyalProgramType.DISCOUNT)
                .build();

        List<LoyalProgram> testListLoyalProgram = new LinkedList<>();
        testListLoyalProgram.add(testLoyalProgramDiscount);
        List<LoyalProgram> testListLoyalProgram1 = new LinkedList<>();
        testListLoyalProgram1.add(testLoyalProgram);

        firstTestTier = Tier.builder()
                .name("FirstTestTier")
                .merchant(testMerchant)
                .loyalPrograms(testListLoyalProgram)
                .tierAmount(BigDecimal.valueOf(100))
                .build();

        secondTier = Tier.builder()
                .name("SecondTestTier")
                .merchant(testMerchant)
                .loyalPrograms(testListLoyalProgram1)
                .tierAmount(BigDecimal.valueOf(150))
                .build();

        List<Tier> testListTier = new LinkedList<>();

        testListTier.add(firstTestTier);
        testListTier.add(secondTier);

        addingService.addTiers(testMerchant.getId(), testListTier);

        testClient = Client.builder()
                .name("TestClient")
                .merchant(testMerchant)
                .tier(firstTestTier)
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
                .card(cardRepository.findById(testClient.getCards().get(0).getId()).orElseThrow())
                .originalPrice(BigDecimal.valueOf(100))
                .build();
    }

    @Test
    public void testMakeSale() {
        saleService.makeSale(testSale);
        String startTier = clientRepository.findById(testClient.getId()).orElseThrow().getTier().getName();
        saleService.makeSale(testSale);
        String finalTier = clientRepository.findById(testClient.getId()).orElseThrow().getTier().getName();
       Assertions.assertEquals("FirstTestTier",startTier);
       Assertions.assertEquals("SecondTestTier",finalTier);

    }

}
