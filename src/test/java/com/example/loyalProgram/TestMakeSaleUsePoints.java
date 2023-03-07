package com.example.loyalProgram;

import com.example.loyalProgram.clientModule.entities.Card;
import com.example.loyalProgram.clientModule.entities.Client;
import com.example.loyalProgram.clientModule.repositories.CardHistoryRepository;
import com.example.loyalProgram.clientModule.repositories.CardRepository;
import com.example.loyalProgram.enums.PointStatus;
import com.example.loyalProgram.loyalPrograms.addPointsLoyalProgram.AddPointsLoyalProgram;
import com.example.loyalProgram.loyalPrograms.baseLoyalProgram.LoyalProgram;
import com.example.loyalProgram.loyalPrograms.discountLoyalProgram.DiscountLoyalProgram;
import com.example.loyalProgram.loyalPrograms.usePointsLoyalProgram.UsePointsLoyalProgram;
import com.example.loyalProgram.merchantModule.entities.Merchant;
import com.example.loyalProgram.merchantModule.entities.Tier;
import com.example.loyalProgram.merchantModule.services.impl.AddingServiceImpl;
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
public class TestMakeSaleUsePoints {
    @Autowired
    SaleService saleService;
    @Autowired
    AddingServiceImpl addingService;
    Merchant testMerchant = mock(Merchant.class);
    Sale testSaleForCreatingCard = mock(Sale.class);
    Sale testSale = mock(Sale.class);
    Client testClient = mock(Client.class);
    LoyalProgram testLoyalProgram = mock(AddPointsLoyalProgram.class);
    LoyalProgram testLoyalProgramDiscount = mock(DiscountLoyalProgram.class);
    LoyalProgram testLoyalProgramUsePoints = mock(UsePointsLoyalProgram.class);
    Tier testTier = mock(Tier.class);
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private CardHistoryRepository cardHistoryRepository;
    @Autowired
    private CardRepository cardRepository;

    @BeforeEach
    public void setUp() {
        testMerchant = Merchant.builder().name("TestMerchant").build();
        addingService.addMerchant(testMerchant);

        testLoyalProgramDiscount = DiscountLoyalProgram.builder()
                .priority(10)
                .discountPercentage(BigDecimal.TEN)
                .build();

        testLoyalProgramUsePoints = UsePointsLoyalProgram.builder()
                .priority(20)
                .build();

        testLoyalProgram = AddPointsLoyalProgram.builder()
                .priority(30)
                .discountPercentage(BigDecimal.TEN)
                .build();

        List<LoyalProgram> testListLoyalProgram = new LinkedList<>();
        testListLoyalProgram.add(testLoyalProgramDiscount);
        testListLoyalProgram.add(testLoyalProgram);
        testListLoyalProgram.add(testLoyalProgramUsePoints);

        testTier = Tier.builder()
                .name("TestTier")
                .merchant(testMerchant)
                .loyalPrograms(testListLoyalProgram)
                .tierAmount(BigDecimal.valueOf(500))
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

        testSaleForCreatingCard = Sale.builder()
                .client(testClient)
                .merchant(testMerchant)
                .originalPrice(BigDecimal.valueOf(100))
                .card(cardRepository.findById(testClient.getCards().get(0).getId()).orElseThrow())
               .usedPoints(BigDecimal.ZERO)
                .build();

        testSale = Sale.builder()
                .client(testClient)
                .merchant(testMerchant)
                .originalPrice(BigDecimal.valueOf(100))
                .card(cardRepository.findById(testClient.getCards().get(0).getId()).orElseThrow())
                .usedPoints(BigDecimal.valueOf(5))
                .build();
    }

    @Test
    public void testMakeSell() {
        saleService.makeSale(testSaleForCreatingCard);
        BigDecimal result = saleService.makeSale(testSale);
        Assertions.assertEquals(result, BigDecimal.valueOf(15).setScale(2, RoundingMode.FLOOR));
        Assertions.assertEquals(saleRepository.findById(testSaleForCreatingCard.getId()).orElseThrow().getId(), testSaleForCreatingCard.getId());
    }

    @Test
    public void testMakeSaleExpirePoints() throws InterruptedException {
        Sale secondSale = testSaleForCreatingCard;
        saleService.makeSale(testSaleForCreatingCard);
        Card card = testSaleForCreatingCard.getCard();
        Thread.sleep(11000);
        saleService.makeSale(secondSale);
        Assertions.assertEquals(cardHistoryRepository.findFirstByCard(card).getPointStatus(), PointStatus.EXPIRED);
    }
}