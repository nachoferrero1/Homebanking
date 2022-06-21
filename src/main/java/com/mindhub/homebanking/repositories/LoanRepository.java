package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource              //CREA UN REPOSITORIO QUE SE VA A BASAR EN LA ARQUITECTURA DE REST
public interface LoanRepository extends JpaRepository<Loan, Long> {

    Loan findByName(String name);

}
