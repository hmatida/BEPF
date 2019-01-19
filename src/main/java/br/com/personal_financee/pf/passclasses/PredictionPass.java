package br.com.personal_financee.pf.passclasses;

import java.util.Calendar;

public class PredictionPass {

    private int periodo;

    private Calendar initDate;

    private Calendar finalDate;

    private int isPay;


    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
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

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

}
