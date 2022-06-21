package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.TypeAccount;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import static com.mindhub.homebanking.utils.Utils.getRandomNumber;



@RestController
@RequestMapping("/api")
public class AccountController {

    /*@Autowired //??
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientController clientController;*/

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;


    @GetMapping("/accounts")
    public List<AccountDTO> getAll(){
        return accountService.getAccountsDTO();
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return accountService.getAccountDTO(id);
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount (Authentication authentication, @RequestParam TypeAccount typeAccount) {
    Client client = clientService.getClientCurrent(authentication);



    if (client.getAccounts().stream().filter(account -> account.getEnabled()==true).count() <3){
        Account account = (new Account(0, LocalDateTime.now(),"VIN" + getRandomNumber(0,99999999),client,typeAccount));
        accountService.saveAccount(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
        else{
        return new ResponseEntity<>("account limit reached", HttpStatus.FORBIDDEN);
        }

    }

    @PatchMapping("/clients/current/accounts")
    public ResponseEntity<Object> disabledAccount (Authentication authentication, @RequestParam String number){

        Account account = accountService.findByNumber(number);
        Client client = clientService.getClientByEmail(authentication.getName());

        if (account.getBalance() != 0){
            return new ResponseEntity<>("no puedes eliminar una cuenta con saldo", HttpStatus.FORBIDDEN);
        }

        if (!client.getAccounts().contains(account)){
            return new ResponseEntity<>("esta cuenta no te pertenece", HttpStatus.FORBIDDEN);
        }

        account.setEnabled(false);
        accountService.saveAccount(account);
        return new ResponseEntity<>("operacion exitosa", HttpStatus.CREATED);

    }



}
