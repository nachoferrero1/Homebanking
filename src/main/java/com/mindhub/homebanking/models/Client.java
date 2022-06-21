package com.mindhub.homebanking.models;

import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") //GENERAN ID DE FORMA AUTOMATICA
    @GenericGenerator(name = "native", strategy = "native") //SIN QUE SE PISEN
    private long id;


    //PROPIEDADES/ATRIBUTOS
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    //RELACION UNO A MUCHOS CON CUENTAS
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>();  //PROPIEDAD SET DE ACCOUNTS


    //RELACION UNO A MUCHOS CON CLIENTLOAN
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();  //PROPIEDAD SET DE CLIENTLOAN

    //RELACION UNO A MUCHOS CON CARD
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();


    //CONSTRUCTORES
    public Client() {}

    public Client (String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    //METODOS ACCESORES = GETTERS Y SETTERS

    public long getId(){
        return id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Account> getAccounts(){
        return accounts;
    }

    public String toString() {
        return firstName + " " + lastName;
    }
    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void addClientLoan(ClientLoan clientLoan) {
        clientLoan.setClient(this);
        clientLoans.add(clientLoan);
    }
    public Set<Card> getCards() {return cards;}


    //METODO GET LOANS
    @JsonIgnore
    public List<Loan> getLoans() {
        return clientLoans.stream().map(client -> client.getLoan()).collect(toList());
    }


    public void addAccount(Account account) {
        account.setOwner(this);
        accounts.add(account);
    }

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
}
