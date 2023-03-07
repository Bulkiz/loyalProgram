//package com.example.loyalProgram;
//
//import com.example.loyalProgram.clientModule.entities.Client;
//import com.example.loyalProgram.clientModule.repositories.CardRepository;
//import com.example.loyalProgram.merchantModule.entities.Merchant;
//import com.example.loyalProgram.merchantModule.entities.Tier;
//import com.example.loyalProgram.merchantModule.services.impl.AddingServiceImpl;
//import com.example.loyalProgram.saleModule.entities.Sale;
//import com.example.loyalProgram.saleModule.repositories.SaleRepository;
//import com.example.loyalProgram.saleModule.services.SaleService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//
//import static org.mockito.Mockito.mock;
//
//
//@SpringBootTest
//@TestPropertySource(locations = "classpath:application.yml")
//public class TestMakeSaleDiscountBirthday {
//    @Autowired
//    SaleService saleService;
//    @Autowired
//    AddingServiceImpl addingService;
//    Merchant testMerchant = mock(Merchant.class);
//    Sale testSale = mock(Sale.class);
//    Client testClient = mock(Client.class);
//    LoyalProgram testLoyalProgram = mock(LoyalProgram.class);
//    LoyalProgram testLoyalProgramBirthday = mock(LoyalProgram.class);
//    Tier testTier = mock(Tier.class);
//    @Autowired
//    private SaleRepository saleRepository;
//    @Autowired
//    private CardRepository cardRepository;
//
//    @BeforeEach
//    public void setUp() {
//        testMerchant = Merchant.builder().name("TestMerchant").build();
//        addingService.addMerchant(testMerchant);
//
//        testLoyalProgram  = LoyalProgram.builder()
//                .name("TestLoyalProgram")
//                .priority(10)
//                .discountPercentage(BigDecimal.TEN)
//                .type(LoyalProgramType.DISCOUNT)
//                .build();
//
//        testLoyalProgramBirthday = LoyalProgram.builder()
//                .name("TestLoyalProgramBirthDay")
//                .priority(5)
//                .discountPercentage(BigDecimal.TEN)
//                .type(LoyalProgramType.BIRTHDAY)
//                .build();
//
//        List<LoyalProgram> testListLoyalProgram = new LinkedList<>();
//        testListLoyalProgram.add(testLoyalProgram);
//        testListLoyalProgram.add(testLoyalProgramBirthday);
//
//        testTier = Tier.builder()
//                .name("TestTier")
//                .merchant(testMerchant)
//                .loyalPrograms(testListLoyalProgram)
//                .tierAmount(BigDecimal.TEN)
//                .build();
//
//        List<Tier> testListTier = new LinkedList<>();
//        testListTier.add(testTier);
//
//        addingService.addTiers(testMerchant.getId(), testListTier);
//
//        testClient = Client.builder()
//                .name("TestClient")
//                .merchant(testMerchant)
//                .tier(testTier)
//                .cards(new ArrayList<>())
//                .birthday(LocalDate.now())
//                .amountSpend(BigDecimal.ZERO)
//                .build();
//
//        List<Client> testListClients = new LinkedList<>();
//        testListClients.add(testClient);
//
//        addingService.addClients(testListClients);
//
//        testSale = Sale.builder()
//                .client(testClient)
//                .merchant(testMerchant)
//                .card(cardRepository.findById(testClient.getCards().get(0).getId()).orElseThrow())
//                .originalPrice(BigDecimal.valueOf(100))
//                .usedPoints(BigDecimal.ZERO)
//                .build();
//    }
//
//    @Test
//    public void testMakeSell() {
//        Assertions.assertEquals(saleService.makeSale(testSale), BigDecimal.valueOf(20).setScale(2));
//        Assertions.assertEquals(saleRepository.findById(testSale.getId()).orElseThrow(), testSale);
//    }
//}
