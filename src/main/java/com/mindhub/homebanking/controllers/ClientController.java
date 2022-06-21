package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.TypeAccount;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.utils.Utils.getRandomNumber;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {

    /*@Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccountRepository accountRepository;*/

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/clients")
    public List<ClientDTO> getAll(){
        return clientService.getClientsDTO();
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientService.getClientDTO(id);
    }


    @PostMapping("/clients")
    public ResponseEntity<Object> register(

            @RequestParam String firstName, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password){


        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientService.getClientByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Client client = (new Client(firstName, lastName, email, passwordEncoder.encode(password)));
        clientService.saveclient(client);

        Account account = (new Account(0, LocalDateTime.now(),"VIN" + getRandomNumber(10000000, 99999999), client, TypeAccount.AHORRO));
        accountService.saveAccount(account);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }


    @GetMapping("/clients/current")
    public ClientDTO getClientAuthentication(Authentication authentication){
        Client client = clientService.getClientCurrent(authentication);
        return new ClientDTO(client);
    }

}






