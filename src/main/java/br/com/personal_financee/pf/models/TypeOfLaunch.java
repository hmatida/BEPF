package br.com.personal_financee.pf.models;

public enum TypeOfLaunch {

    C("Entrada"),
    S("Saída"),
    T("Transferência");

    private String descricao;

    TypeOfLaunch(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
