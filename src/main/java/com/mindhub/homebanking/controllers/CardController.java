package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping ("/api")
public class CardController{

    @Autowired
    private CardRepository cardRepository;

    /*@Autowired
    private ClientRepository clientRepository;*/

    @Autowired
    private ClientService clientService;

    @Autowired
    private CardService cardService;

    /*@Autowired
    private ClientController clientController;*/


    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(Authentication authentication, @RequestParam CardColor color, @RequestParam CardType type){

        Client client = clientService.getClientCurrent(authentication);
        LocalDate today = LocalDate.now();
        List<Card> listCardsDebit = client.getCards().stream().filter(cardEnabled -> cardEnabled.getEnabled() == true).filter(card -> card.getType().equals(CardType.DEBIT)).collect(Collectors.toList());
        List<Card> listCardsCredit = client.getCards().stream().filter(cardEnabled -> cardEnabled.getEnabled() == true).filter(card -> card.getType().equals(CardType.DEBIT)).collect(Collectors.toList());


        if (color.toString().isEmpty() || type.toString().isEmpty()) {
        return new ResponseEntity<>("void data", HttpStatus.FORBIDDEN);
        }

        if (listCardsDebit.size() >= 3 && type == CardType.DEBIT ) {
            return new ResponseEntity<>("You already have 3 debit cards", HttpStatus.FORBIDDEN);
        }

        if (listCardsCredit.size() >= 3 && type == CardType.DEBIT ) {
            return new ResponseEntity<>("You already have 3 credit cards", HttpStatus.FORBIDDEN);
        }

        String cardNumber = CardUtils.getCardNumber();
        int cvv = CardUtils.getCVV();


        Card card = (new Card((client.getFirstName() + client.getLastName()), type, color, cardNumber, cvv, today, LocalDate.now().plusYears(5),client));
        cardService.saveCard(card);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

        @PatchMapping("/clients/current/cards")
        public ResponseEntity<Object> deleteCard (Authentication authentication, @RequestParam String number){

            Card card = cardService.findByNumber(number);
            Client client = clientService.getClientByEmail(authentication.getName());

            if (number.isEmpty()){
                return new ResponseEntity<>("campo vacio", HttpStatus.FORBIDDEN);
            }

            if (!client.getCards().contains(card)){
                return new ResponseEntity<>("esta tarjeta no te pertenece", HttpStatus.FORBIDDEN);
            }

            card.setEnabled(false);
            cardService.saveCard(card);
            return new ResponseEntity<>(HttpStatus.CREATED);

        }



}
