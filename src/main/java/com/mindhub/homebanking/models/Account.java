package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;  //importar clase para detectar fecha actual
import java.util.HashSet;
import java.util.Set;


@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") //GENERAN ID DE FORMA AUTOMATICA
    @GenericGenerator(name = "native", strategy = "native") //SIN QUE SE PISEN
    private long id;


    //PROPIEDADES DE LA CLASE
    private double balance;
    private LocalDateTime creationDate;
    private String number;
    private Boolean enabled;
    private TypeAccount typeAccount;


    //RELACION MUCHOS A UNO CON CLIENTE
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="owner_id")
    private Client owner;

    //RELACION UNO A MUCHOS CON TRANSACCIONES
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();  //PROPIEDAD SET DE TRANSACTIONS


    //CONSTRUCTORES
    public Account() { }

    public Account(double balance, LocalDateTime creationDate, String number, Client cliente, TypeAccount typeAccount) {
        this.balance = balance;  //ROJOS TIPOS DE DATOS & NARANJAS PARAMETROS
        this.creationDate = creationDate;
        this.number = number;
        this.owner = cliente;
        this.enabled = true;
        this.typeAccount = typeAccount;

    }

    //METODOS ACCESORES

    public long getId() {  //EL SET NO SE DEBE COLOCAR PARA QUE OTROS DESARROLLADORES NO PUEDAN CAMBIAR EL ID
        return id;
    }

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


    public Client getOwner() {
        return owner;
    }
    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public Set<Transaction> getTransactions() {return transactions;}
    public void setTransactions(Set<Transaction> transactions) {this.transactions = transactions;}

    public Boolean getEnabled() {return enabled;}
    public void setEnabled(Boolean enabled) {this.enabled = enabled;}

    public TypeAccount getTypeAccount() {return typeAccount;}
    public void setTypeAccount(TypeAccount typeAccount) {this.typeAccount = typeAccount;}
}
