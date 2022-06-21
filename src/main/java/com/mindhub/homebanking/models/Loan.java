package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") //GENERAN ID DE FORMA AUTOMATICA
    @GenericGenerator(name = "native", strategy = "native") //SIN QUE SE PISEN
    private long id;

    //PROPIEDADES
    private String name;
    private int maxAmount;

    private float percentage;

    @ElementCollection
    @Column(name="payments")
    private List<Integer> payments = new ArrayList<>();

    //RELACION UNO A MUCHOS CON CLIENT LOAN
    @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();  //PROPIEDAD SET DE CLIENTLOAN

    public void addClientLoan(ClientLoan clientLoan) {
        clientLoan.setLoan(this);
        clientLoans.add(clientLoan);
    }


    //METODO GET CLIENTS
    public List<Client> getClients() {
        return clientLoans.stream().map(ClientLoan::getClient).collect(Collectors.toList());
    }


    //CONSTRUCTORES (SOBRE CARGA DE DATOS)
    public Loan () {}

    public Loan(String name, int maxAmount, List<Integer> payments, float percentage) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.percentage = percentage;
    }

    //GETTERS Y SETTERS

    public long getId() {return id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getMaxAmount() {return maxAmount;}
    public void setMaxAmount(int maxAmount) {this.maxAmount = maxAmount;}

    public List<Integer> getPayments() {return payments;}
    public void setPayments(List<Integer> payments) {this.payments = payments;}

    public float getPercentage() {return percentage;}
    public void setPercentage(float percentage) {this.percentage = percentage;}
}
