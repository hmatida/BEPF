package br.com.personal_financee.pf.models;

import javax.persistence.*;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_account;

    private String name_account;

    private Double balance;

    private Double startBalance;

    private Double limite;

    private TypeOfAccount typeOfAccount;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Users user;

    private boolean principal = false;

    public Long getId_account() {
        return id_account;
    }

    public void setId_account(Long id_account) {
        this.id_account = id_account;
    }

    public String getName_account() {
        return name_account;
    }

    public void setName_account(String name_account) {
        this.name_account = name_account;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public TypeOfAccount getTypeOfAccount() {
        return typeOfAccount;
    }

    public void setTypeOfAccount(TypeOfAccount typeOfAccount) {
        this.typeOfAccount = typeOfAccount;
    }

    public Double getLimite() {
        return limite;
    }

    public void setLimite(Double limite) {
        this.limite = limite;
    }

    public Double getStartBalance() {
        return startBalance;
    }

    public void setStartBalance(Double startBalance) {
        this.startBalance = startBalance;
        this.balance = startBalance;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
