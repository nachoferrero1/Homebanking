package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    /*@Autowired
    private ClientRepository clientRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Autowired
    private AccountRepository accountRepository;*/

    @Autowired
    private AccountService accountService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientLoanService clientLoanService;


    @GetMapping("/loans")
    public List<LoanDTO> getLoansDto(){
        return loanService.getLoansDTO();
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createLoan (@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){

        Double montoSolicitado = loanApplicationDTO.getAmount();
        int cuotas = loanApplicationDTO.getPayments();
        Account cuentaDestino = accountService.findByNumber(loanApplicationDTO.getNumeroDestino());
        Loan loan = loanService.getLoan(loanApplicationDTO.getIdLoan());
        Client client = clientService.getClientCurrent(authentication);
        LocalDateTime today = LocalDateTime.now().withNano(0);

        if(montoSolicitado == 0 || cuotas == 0){
            return new ResponseEntity<>("el monto o las cuotas no pueden ser 0", HttpStatus.FORBIDDEN);
        }

        if (loan == null){
            return new ResponseEntity<>("el prestamo no existe", HttpStatus.FORBIDDEN);
        }

        String nombre = loan.getName();

        if (montoSolicitado > loan.getMaxAmount()){
            return new ResponseEntity<>("el monto solicitado supera el limite del prestamo", HttpStatus.FORBIDDEN);
        }

        if (!loan.getPayments().contains(cuotas)){
            return new ResponseEntity<>("la opcion de cuotas que intenta elegir no existe", HttpStatus.FORBIDDEN);
        }

        if(cuentaDestino == null){
            return new ResponseEntity<>("La cuenta de destino no existe", HttpStatus.FORBIDDEN);
        }

        if(!client.getAccounts().contains(cuentaDestino)){
            return new ResponseEntity<>("la cuenta no pertenece al cliente autenticado", HttpStatus.FORBIDDEN);
        }

        if (client.getLoans().stream().filter(loan1 -> loan1.getId() == loanApplicationDTO.getIdLoan()).count() >= 1) {
            return new ResponseEntity<>("loan limit reached", HttpStatus.FORBIDDEN);
        }

        Double montoConInteres = montoSolicitado * loan.getPercentage();

        clientLoanService.saveClientLoan(new ClientLoan(montoConInteres, cuotas, client, loan));
        transactionService.saveTransaction(new Transaction(TransactionType.CREDITO, montoSolicitado,nombre + "loan approved",today, cuentaDestino));

        cuentaDestino.setBalance(cuentaDestino.getBalance() + montoSolicitado);
        accountService.saveAccount(cuentaDestino);

        return new ResponseEntity<>("Prestamo creado", HttpStatus.CREATED);

    }

    @PostMapping("/loan")
    public ResponseEntity<Object> createNewLoan (Authentication authentication, @RequestParam String name, @RequestParam List<Integer> payments, @RequestParam int maxAmount, @RequestParam float percentage){

        Client client = clientService.getClientCurrent(authentication);

        if (client.getEmail() != "admin@gmail.com"){
            return new ResponseEntity<>("you are not admin", HttpStatus.FORBIDDEN);
        }

        if (name.isEmpty()){
            return new ResponseEntity<>("is empty", HttpStatus.FORBIDDEN);
        }

        if (loanService.findByName(name)!= null){
            return new ResponseEntity<>("loan exist", HttpStatus.FORBIDDEN);
        }

        Loan loan = new Loan(name, maxAmount, payments,percentage);
        loanService.saveLoan(loan);

        return new ResponseEntity<>("Loan created", HttpStatus.CREATED);
    }

}
