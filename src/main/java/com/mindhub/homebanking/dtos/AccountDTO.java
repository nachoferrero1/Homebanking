package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.TypeAccount;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    //PROPIEDADES
    private double balance;
    private LocalDateTime creationDate;
    private String number;
    private long id;
    private Set<TransactionDTO> transactions;
    private Boolean enabled;

    private TypeAccount typeAccount;




    //CONSTRUCTORES
    public AccountDTO(){}

    public AccountDTO(Account account) {
        this.balance = account.getBalance();
        this.creationDate = account.getCreationDate();
        this.number = account.getNumber();
        this.id = account.getId();
        this.transactions = account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
        this.enabled = account.getEnabled();
        this.typeAccount = account.getTypeAccount();
    }


    //GETTERS AND SETTERS
    public long getId() {return id;}

    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public Set<TransactionDTO> getTransactions() {return transactions;}
    public void setTransactions(Set<TransactionDTO> transactions) {this.transactions = transactions;}

    public Boolean getEnabled() {return enabled;}
    public void setEnabled(Boolean enabled) {this.enabled = enabled;}

    public TypeAccount getTypeAccount() {return typeAccount;}
    public void setTypeAccount(TypeAccount typeAccount) {this.typeAccount = typeAccount;}}
