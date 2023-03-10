package com.example.loyalProgram.saleModule.services.impl;


import com.example.loyalProgram.clientModule.entities.Client;
import com.example.loyalProgram.clientModule.repositories.ClientRepository;
import com.example.loyalProgram.loyalPrograms.baseLoyalProgram.LoyalProgram;
import com.example.loyalProgram.loyalPrograms.baseLoyalProgram.LoyalProgramService;
import com.example.loyalProgram.merchantModule.entities.Tier;
import com.example.loyalProgram.merchantModule.repositories.LoyalProgramRepository;
import com.example.loyalProgram.merchantModule.repositories.TierRepository;
import com.example.loyalProgram.saleModule.entities.Sale;
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

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TierRepository tierRepository;
    @Autowired
    private LoyalProgramRepository loyalProgramRepository;
    @Autowired
    private ApplicationContext applicationContext;
    Map<String, LoyalProgramService> beansOfType;
    @Autowired
    private SaleRepository saleRepository;

    @PostConstruct
    private void postInit() {
        beansOfType = applicationContext.getBeansOfType(LoyalProgramService.class);
    }

    @Override
    @Transactional
    public Sale makeSale(Sale sale) {
        List<LoyalProgram> loyalPrograms = getLoyalProgramsSorted(sale);
        saleRepository.save(sale);
        loyalPrograms.forEach(loyalProgram -> {
            String simpleName = loyalProgram.getClass().getSimpleName();
            simpleName = simpleName.substring(0,1).toLowerCase() + simpleName.substring(1);
            simpleName = simpleName + "Service";
            beansOfType.get(simpleName).applyProgram(sale, loyalProgram);
        });
        updateAmountAndCheckTier(sale.getClient(), sale.getSummaryPrice());
        return sale;
    }

    private void updateAmountAndCheckTier(Client client, BigDecimal summaryPrice) {
        client.setAmountSpend(client.getAmountSpend().add(summaryPrice));
        Tier nextTier = tierRepository.
                findFirstByMerchantAndTierAmountGreaterThanOrderByTierAmount(client.getMerchant(),
                        client.getTier().getTierAmount());
        if (Objects.nonNull(nextTier) && nextTier.getTierAmount().compareTo(client.getAmountSpend()) <= 0) {
                client.setTier(nextTier);
        }
        clientRepository.save(client);
    }

    private List<LoyalProgram> getLoyalProgramsSorted(Sale sale) {
        Client client = clientRepository.findById(sale.getClient().getId()).orElseThrow();
        Tier currTier = tierRepository.findById(client.getTier().getId()).orElseThrow();
        return loyalProgramRepository.findAllByTier(currTier.getId());
    }

}
