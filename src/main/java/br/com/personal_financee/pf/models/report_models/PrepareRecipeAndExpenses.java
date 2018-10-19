package br.com.personal_financee.pf.models.report_models;

import br.com.personal_financee.pf.models.TypeOfLaunch;

public class PrepareRecipeAndExpenses {

    private TypeOfLaunch typeOfLaunch;

    private Double sumValue;

    public PrepareRecipeAndExpenses(TypeOfLaunch typeOfLaunch, Double sumValue) {
        this.typeOfLaunch = typeOfLaunch;
        this.sumValue = sumValue;
    }

    public TypeOfLaunch getTypeOfLaunch() {
        return typeOfLaunch;
    }

    public void setTypeOfLaunch(TypeOfLaunch typeOfLaunch) {
        this.typeOfLaunch = typeOfLaunch;
    }

    public Double getSumValue() {
        return sumValue;
    }

    public void setSumValue(Double sumValue) {
        this.sumValue = sumValue;
    }
}
