package br.com.personal_financee.pf.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_bank;

    private String name_bank;

    public Long getId_bank() {
        return id_bank;
    }

    public void setId_bank(Long id_bank) {
        this.id_bank = id_bank;
    }

    public String getName_bank() {
        return name_bank;
    }

    public void setName_bank(String name_bank) {
        this.name_bank = name_bank;
    }
}
