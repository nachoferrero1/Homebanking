package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/api")
public class TransactionController {


    /*@Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientRepository clientRepository;*/

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> transaction(
            @RequestParam double amount, @RequestParam String description,

            @RequestParam String numberOrigen, @RequestParam String numberDestiny, Authentication authentication){


        Client client = clientService.getClientByEmail(authentication.getName());
        Account cuentaOrigen =  accountService.findByNumber(numberOrigen);
        Account cuentaDestino =  accountService.findByNumber(numberDestiny);
        LocalDateTime today = LocalDateTime.now().withNano(0);


        if (amount <= 0 || description.isEmpty() || numberOrigen == null || numberDestiny == null) {
            return new ResponseEntity<>("Campos Vacios", HttpStatus.FORBIDDEN);
        }

        if (numberOrigen.equals(numberDestiny)) {
            return new ResponseEntity<>("no puedes enviar dinero a tu propia cuenta", HttpStatus.FORBIDDEN);
        }

        if(cuentaOrigen == null){
            return new ResponseEntity<>("La cuenta de origen no existe", HttpStatus.FORBIDDEN);
        }

        if(!client.getAccounts().contains(cuentaOrigen)){
            return new ResponseEntity<>("la cuenta no pertenece al cliente autenticado", HttpStatus.FORBIDDEN);
        }

        if(cuentaDestino == null){
            return new ResponseEntity<>("La cuenta de destino no existe", HttpStatus.FORBIDDEN);
        }

        if(amount > (cuentaOrigen.getBalance())){
            return new ResponseEntity<>("no tienes fondos suficientes", HttpStatus.FORBIDDEN);
        }

        Transaction transaction = new Transaction(TransactionType.DEBITO, amount * -1 ,cuentaOrigen.getNumber() + "  -  " + description, today, cuentaOrigen);
        transactionService.saveTransaction(transaction);

        Transaction transaction2 = new Transaction(TransactionType.CREDITO, amount, cuentaDestino.getNumber() + "  -  " +description, today, cuentaDestino);
        transactionService.saveTransaction(transaction2);

        cuentaOrigen.setBalance(cuentaOrigen.getBalance() - amount);
        accountService.saveAccount(cuentaOrigen);

        cuentaDestino.setBalance(cuentaDestino.getBalance() + amount);
        accountService.saveAccount(cuentaDestino);

        return new ResponseEntity<>("transaction completed successfully",HttpStatus.CREATED);

    }


}

