package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@SpringBootApplication
public class HomebankingApplication {
    public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

    @Autowired
    private PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(ClientRepository repository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {

            //CREAR CLIENTE Y GAURDARLO EN LA BASE DE DATOS


			//CLIENTES
			Client cliente1 = new Client ("melba", "morel", "melba@mindhub.com", passwordEncoder.encode("melba"));
            repository.save(cliente1);

            Client cliente2 = new Client ("juan", "perez", "juanperez@gmail.com",passwordEncoder.encode("pepeArgento123"));
            repository.save(cliente2);

			Client cliente3 = new Client("admin", "admin", "admin@gmail.com", passwordEncoder.encode("administrador"));
			repository.save(cliente3);


			//CUENTAS
			Account cuenta1 = new Account(5000, LocalDateTime.now().withNano(0),"VIN001", cliente1, TypeAccount.CORRIENTE);
			/*cliente1.addAccount(cuenta1);*/
			accountRepository.save(cuenta1);

			Account cuenta2 = new Account(7500, LocalDateTime.now().plusDays(1).withNano(0), "VIN002", cliente1, TypeAccount.AHORRO);
			/*cliente1.addAccount(cuenta2);*/
			accountRepository.save(cuenta2);

            Account cuenta3 = new Account(9000, LocalDateTime.now().plusDays(1).withNano(0), "VIN003", cliente1, TypeAccount.CORRIENTE);
            /*cliente1.addAccount(cuenta2);*/
            accountRepository.save(cuenta3);

			Account cuenta4 = new Account(10000, LocalDateTime.now().plusDays(3).withNano(0), "VIN004", cliente2, TypeAccount.AHORRO);
			accountRepository.save(cuenta4);



            //TRANSACCIONES
            Transaction transaccion1 = new Transaction (TransactionType.CREDITO, 3000, "ingreso de ganancias por inversiones", LocalDateTime.now().withNano(0), cuenta1);
			transactionRepository.save(transaccion1);

			Transaction transaccion2 = new Transaction (TransactionType.DEBITO, 1500, "pago de mueble", LocalDateTime.now().withNano(0), cuenta1);
			transactionRepository.save(transaccion2);

			Transaction transaccion3 = new Transaction (TransactionType.CREDITO, 2100, "ingreso de ganancias por inversiones", LocalDateTime.now().withNano(0), cuenta1);
			transactionRepository.save(transaccion3);

			Transaction transaccion4 = new Transaction (TransactionType.DEBITO, 500, "pago de netflix", LocalDateTime.now().withNano(0), cuenta4);
			transactionRepository.save(transaccion4);



            //PRESTAMOS
            Loan prestamo1 = new Loan("Hipotecario", 500000, List.of(12,24,36,48,60), 2f);
            loanRepository.save(prestamo1);

            Loan prestamo3 = new Loan("Automotriz", 300000, List.of(6,12,24,36),3f);
            loanRepository.save(prestamo3);

            Loan prestamo2 = new Loan("Personal", 100000, List.of(6,12,24),4f);
            loanRepository.save(prestamo2);



			//CLIENTS-LOANS
			ClientLoan clientLoan1 = new ClientLoan(400000d, 60 , cliente1, prestamo1);
			clientLoanRepository.save(clientLoan1);

			ClientLoan clientLoan2 = new ClientLoan(50000d, 12, cliente1, prestamo2);
			clientLoanRepository.save(clientLoan2);


			ClientLoan clientLoan3 = new ClientLoan(100000d, 24, cliente2, prestamo2);
			clientLoanRepository.save(clientLoan3);

			ClientLoan clientLoan4 = new ClientLoan(200000d, 36, cliente2, prestamo3);
			clientLoanRepository.save(clientLoan4);



			//CARDS
			Card card1 = new Card("Melba Morel", CardType.DEBIT, CardColor.GOLD, "1020-3040-5060-7080", 123, LocalDate.now(), LocalDate.now().plusYears(5) ,cliente1);
            cardRepository.save(card1);

            Card card2 = new Card("Melba Morel", CardType.CREDIT, CardColor.TITANIUM, "1122-3344-5566-7788", 456, LocalDate.now(), LocalDate.now().plusYears(5) ,cliente1);
            cardRepository.save(card2);

            Card card3 = new Card("Juan Perez", CardType.CREDIT, CardColor.SILVER, "1234-5678-9012-3456", 789, LocalDate.now(), LocalDate.now().plusYears(5), cliente2);
            cardRepository.save(card3);

			Card card4 = new Card("Juan Perrez", CardType.CREDIT, CardColor.SILVER, "1234-5678-9012-8456", 589, LocalDate.now(), LocalDate.now().minusYears(2), cliente1);
			cardRepository.save(card4);
		};
	}
}
