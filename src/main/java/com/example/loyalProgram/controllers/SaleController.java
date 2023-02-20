package com.example.loyalProgram.controllers;

import com.example.loyalProgram.saleModule.DTOs.SaleDTO;
import com.example.loyalProgram.saleModule.DTOs.SaleMapper;
import com.example.loyalProgram.services.RedeemPointsService;
import com.example.loyalProgram.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sale")
public class SaleController {
    @Autowired private SaleService saleService;
    @Autowired private SaleMapper saleMapper;
    @Autowired private RedeemPointsService redeemPointsService;

    @PostMapping
    public void makeSell(@RequestBody SaleDTO saleDTO){
        saleService.makeSale(saleMapper.saleMapper(saleDTO));
    }
    @PostMapping("/updateByDate")
    public void updateByDate(){
        redeemPointsService.updateStatusByDate();
    }
}
