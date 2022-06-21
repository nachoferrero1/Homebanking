package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource //ESTE RECURSO SERA ACCEDIDO MEDIANTE LAS RESTRICCIONES DE REST (POST,PUT,DELETE,GET)
public interface TransactionRepository extends JpaRepository<Transaction, Long> { //HEREDA LOS METODOS DE JPAREPOSITORY Y LOS METODOS TRABAJAN CON LOS PARAMETROS (TRANSACTIONS Y LONG)
}
