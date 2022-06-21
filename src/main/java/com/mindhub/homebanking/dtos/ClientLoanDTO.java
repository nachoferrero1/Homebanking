package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {

    //PROPIEDADES
    private long id;
    private long loanId;
    private String name;
    private Double amount;
    private int payments;

    //CONSTRUCTORES
    public ClientLoanDTO () {}

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.loanId = clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
    }

    //GETTERS Y SETTERS

    public long getId() {return id;}

    public long getLoanId() {return loanId;}

    public String getName() {return name;}

    public Double getAmount() {return amount;}

    public int getPayments() {return payments;}
}
