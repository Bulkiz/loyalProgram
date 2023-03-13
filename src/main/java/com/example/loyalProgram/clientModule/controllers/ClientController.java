package com.example.loyalProgram.clientModule.controllers;

import com.example.loyalProgram.clientModule.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired private ClientService clientService;

    @PostMapping("/{clientId}/addCard")
    public String addCardByClientId(@PathVariable Integer clientId){
         clientService.addCard(clientId);
        return "You successfully created new card!";
    }

    @PostMapping("disableCard/{cardId}")
    public String disableCardByClientId(@PathVariable Integer cardId){
        clientService.disableCard(cardId);
        return "You successfully disabled the card!";
    }

}
