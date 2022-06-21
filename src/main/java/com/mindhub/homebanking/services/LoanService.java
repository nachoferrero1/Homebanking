package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.Loan;

import java.util.List;

public interface LoanService {

    Loan getLoan(long id);
    List<LoanDTO> getLoansDTO();

    Loan findByName (String name);
    void saveLoan(Loan loan);

}
