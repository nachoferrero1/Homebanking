package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity //GENERA UNA TABLA EN LA BASE DE DATOS
public class Transaction {
    @Id //ANOTA LA LLAVE PRIMARIA
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") //GENERAN ID DE FORMA AUTOMATICA
    @GenericGenerator(name = "native", strategy = "native") //SIN QUE SE PISEN
    private long id;

    //PROPIEDADES
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDateTime date;

    private double accountBalance;




    //RELACION MUCHOS A UNO
    @ManyToOne(fetch = FetchType.EAGER) //eager impaciente
    @JoinColumn(name="account_id")
    private Account account;



    //CONSTRUCTORES
    public Transaction() {}

    public Transaction(TransactionType type, double amount, String description, LocalDateTime date, Account account) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.account = account;
        this.accountBalance = account.getBalance() + amount;
    }


    //GETTERS AND SETTERS

    public long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }
    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Account getAccount() {return account;}
    public void setAccount(Account account) {this.account = account;}

    public double getAccountBalance() {return accountBalance;}
    public void setAccountBalance(double accountBalance) {this.accountBalance = accountBalance;}
}
