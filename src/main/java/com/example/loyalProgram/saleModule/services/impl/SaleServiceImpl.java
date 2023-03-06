package com.example.loyalProgram.saleModule.services.impl;


import com.example.loyalProgram.clientModule.entities.Client;
import com.example.loyalProgram.clientModule.repositories.CardHistoryRepository;
import com.example.loyalProgram.clientModule.repositories.CardRepository;
import com.example.loyalProgram.clientModule.repositories.ClientRepository;
import com.example.loyalProgram.merchantModule.entities.Tier;
import com.example.loyalProgram.merchantModule.entities.loyals.LoyalProgram;
import com.example.loyalProgram.merchantModule.entities.services.LoyalProgramService;
import com.example.loyalProgram.merchantModule.repositories.LoyalProgramRepository;
import com.example.loyalProgram.merchantModule.repositories.MerchantRepository;
import com.example.loyalProgram.merchantModule.repositories.TierRepository;
import com.example.loyalProgram.saleModule.entities.Sale;
import com.example.loyalProgram.saleModule.repositories.SaleBonusRepository;
import com.example.loyalProgram.saleModule.repositories.SaleRepository;
import com.example.loyalProgram.saleModule.services.SaleService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private MerchantRepository merchantRepository;
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CardHistoryRepository cardHistoryRepository;
    @Autowired
    private TierRepository tierRepository;
    @Autowired
    private LoyalProgramRepository loyalProgramRepository;
    @Autowired
    private SaleBonusRepository saleBonusRepository;
    @Autowired
    private ApplicationContext applicationContext;
    Map<String, LoyalProgramService> beansOfType;

    @PostConstruct
    private void postInit() {
        beansOfType = applicationContext.getBeansOfType(LoyalProgramService.class);
    }

    @Override
    @Transactional
    public BigDecimal makeSale(Sale currSale) {
        List<LoyalProgram> loyalPrograms = getLoyalProgramsSorted(currSale);
        loyalPrograms.forEach(loyalProgram -> {
            String simpleName = loyalProgram.getClass().getSimpleName();
            simpleName = simpleName.substring(0,1).toLowerCase() + simpleName.substring(1);
            simpleName = simpleName + "Service";
            beansOfType.get(simpleName).applyProgram(currSale, loyalProgram);
        });
        updateAmountAndCheckTier(currSale.getClient(), currSale.getSummaryPrice());
        return currSale.getDiscountedPrice();
    }

    private void updateAmountAndCheckTier(Client client, BigDecimal summaryPrice) {
        client.setAmountSpend(client.getAmountSpend().add(summaryPrice));
        if (client.getTier().getTierAmount().compareTo(client.getAmountSpend()) <= 0) {
            Tier tier = tierRepository.
                    findFirstByMerchantAndTierAmountGreaterThanOrderByTierAmount(client.getMerchant(),
                            client.getTier().getTierAmount());
            if (Objects.nonNull(tier)){
                client.setTier(tier);
            }
        }
        clientRepository.save(client);
    }

    private List<LoyalProgram> getLoyalProgramsSorted(Sale sale) {
        Client client = clientRepository.findById(sale.getClient().getId()).orElseThrow();
        Tier currTier = tierRepository.findById(client.getTier().getId()).orElseThrow();
        return loyalProgramRepository.findAllByTier(currTier);
    }

}
