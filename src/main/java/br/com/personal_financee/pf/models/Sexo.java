package br.com.personal_financee.pf.models;

public enum Sexo {

    MASC("Masculino"),
    FEM("Feminino");

    private String descricao;

    Sexo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
