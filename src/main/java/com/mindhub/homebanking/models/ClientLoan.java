package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class ClientLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") //GENERAN ID DE FORMA AUTOMATICA
    @GenericGenerator(name = "native", strategy = "native") //SIN QUE SE PISEN
    private long id;

    //PROPIEDADES
    private Double amount;
    private int payments;

    //RELACION MUCHOS A UNO CON CLIENTE
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    //RELACION MUCHOS A UNO CON LOAN
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="loan_id")
    private Loan loan;



    //CONSTRUCTORES
    public ClientLoan () {}

    public ClientLoan(Double amount, int payments, Client client, Loan loan) {
        this.amount = amount;
        this.payments = payments;
        this.client = client;
        this.loan = loan;
    }

    //GETTERS Y SETTERS

    public long getId() {return id;}


    public Double getAmount() {return amount;}
    public void setAmount(Double amount) {this.amount = amount;}

    public int getPayments() {return payments;}
    public void setPayments(int payments) {this.payments = payments;}

    public Client getClient() {return client;}
    public void setClient(Client client) {this.client = client;}

    public Loan getLoan() {return loan;}
    public void setLoan(Loan loan) {this.loan = loan;}
}
