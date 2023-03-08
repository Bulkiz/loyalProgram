package com.example.loyalProgram.saleModule.controllers;

import com.example.loyalProgram.saleModule.DTOs.SaleDTO;
import com.example.loyalProgram.saleModule.DTOs.SaleMapper;
import com.example.loyalProgram.saleModule.services.SaleService;
import jakarta.validation.Valid;
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

    @PostMapping
        public String makeSell(@Valid @RequestBody SaleDTO saleDTO){
        return "You have successfully saved "  + saleService.makeSale(saleMapper.saleMapper(saleDTO)) + " !";
    }

}
