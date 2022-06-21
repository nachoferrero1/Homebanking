package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@SpringBootTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {


        /*TESTS ACCOUNTS*/
        @Autowired
        AccountRepository accountRepository;

        @Test
        public void existAccounts(){
            List<Account> accounts = accountRepository.findAll();
            assertThat(accounts,is(not(empty())));
        }

        @Test
        public void existAccountNumber(){
            List<Account> accounts = accountRepository.findAll();
            assertThat(accounts, hasItem(hasProperty("balance", is(5000d))));
        }

        /*TESTS CARDS*/
        @Autowired
        CardRepository cardRepository;

        @Test
        public void existCards(){
            List<Card> cards = cardRepository.findAll();
            assertThat(cards,is(not(empty())));
        }

        @Test
        public void existCardsCredit(){
            List<Card> cards = cardRepository.findAll();
            assertThat(cards, hasItem(hasProperty("type", is(CardType.CREDIT))));
        }

        /*TEST CLIENTS*/
        @Autowired
        ClientRepository clientRepository;

        @Test
        public void existClient(){
            List<Client> clients = clientRepository.findAll();
            assertThat(clients,is(not(empty())));
        }

        @Test
        public void existNameClient(){
            List<Client> clients = clientRepository.findAll();
            assertThat(clients, hasItem(hasProperty("firstName", is("melba"))));
        }

        /*TESTS LOANS*/
        @Autowired
        LoanRepository loanRepository;

        @Test
        public void existLoans(){
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans,is(not(empty())));
        }

        @Test
        public void existPersonalLoan(){
            List<Loan> loans = loanRepository.findAll();
            assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
        }

        /*TESTS TRANSACTIONS*/
        @Autowired
        TransactionRepository transactionRepository;

        @Test
        public void existTransaction(){
            List<Transaction> transactions = transactionRepository.findAll();
            assertThat(transactions,is(not(empty())));
        }

        @Test
        public void existCreditTransaction(){
            List<Transaction> transactions = transactionRepository.findAll();
            assertThat(transactions, hasItem(hasProperty("amount", is(3000d))));
        }

    }


