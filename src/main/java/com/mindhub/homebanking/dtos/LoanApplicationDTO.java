package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;

public class LoanApplicationDTO {

    //PROPIEDADES/ATRIBUTOS

    private long idLoan;
    private Double amount;
    private int payments;
    private String numeroDestino;


    //CONSTRUCTORES

    public LoanApplicationDTO(){}

    public LoanApplicationDTO(ClientLoanDTO clientLoanDTO, Transaction transaction){
        this.idLoan = clientLoanDTO.getId();
        this.amount = clientLoanDTO.getAmount();
        this.payments = clientLoanDTO.getPayments();
        this.numeroDestino = transaction.getAccount().getNumber();
    }

    //GETTERS AND SETTERS

    public long getIdLoan() {return idLoan;}

    public Double getAmount() {return amount;}
    public void setAmount(Double amount) {this.amount = amount;}

    public int getPayments() {return payments;}
    public void setPayments(int payments) {this.payments = payments;}

    public String getNumeroDestino() {return numeroDestino;}
    public void setNumeroDestino(String numeroDestino) {this.numeroDestino = numeroDestino;}
}
