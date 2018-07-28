package br.com.personal_financee.pf.models;

public enum TypeOfAccount {

    CD("Cartão de crédito"),
    CC("Conta Corrente"),
    CP("Conta Poupança"),
    CI("Conta Investimento"),
    CT("Em Carteira");


    private String descricao;

    TypeOfAccount(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
