package br.com.personal_financee.pf.passclasses;

import br.com.personal_financee.pf.models.Account;

import java.util.Calendar;

public class ExtractFilter {

    private Account account;

    private int operation;

    private Calendar initDate;

    private Calendar finalDate;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public Calendar getInitDate() {
        return initDate;
    }

    public void setInitDate(Calendar initDate) {
        this.initDate = initDate;
    }

    public Calendar getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Calendar finalDate) {
        this.finalDate = finalDate;
    }
}
