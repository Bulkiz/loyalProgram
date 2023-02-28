package com.example.loyalProgram.clientModule.controllers;

import com.example.loyalProgram.clientModule.entities.Card;
import com.example.loyalProgram.merchantModule.services.AddingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired AddingService addingService;

}
