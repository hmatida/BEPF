package br.com.personal_financee.pf.utility;

import br.com.personal_financee.pf.models.Launches;

public class Transfer {

    private Launches credito;

    private Launches debito;

    public Launches getCredito() {
        return credito;
    }

    public void setCredito(Launches credito) {
        this.credito = credito;
    }

    public Launches getDebito() {
        return debito;
    }

    public void setDebito(Launches debito) {
        this.debito = debito;
    }
}
