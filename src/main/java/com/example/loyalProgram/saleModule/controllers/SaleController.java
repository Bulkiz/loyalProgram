package com.example.loyalProgram.saleModule.controllers;

import com.example.loyalProgram.saleModule.DTOs.InputSaleDTO;
import com.example.loyalProgram.saleModule.DTOs.OutputSaleDTO;
import com.example.loyalProgram.saleModule.DTOs.SaleMapper;
import com.example.loyalProgram.saleModule.services.SaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        public ResponseEntity<OutputSaleDTO> makeSell(@Valid @RequestBody InputSaleDTO inputSaleDTO){
        return new ResponseEntity<>(saleMapper.mapOutput
                (saleService.makeSale(saleMapper.mapInput(inputSaleDTO))), HttpStatus.OK);
    }
}
