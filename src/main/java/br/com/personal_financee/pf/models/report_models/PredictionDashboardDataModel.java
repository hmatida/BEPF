package br.com.personal_financee.pf.models.report_models;

import br.com.personal_financee.pf.models.TypeOfLaunch;

import java.util.Calendar;

public class PredictionDashboardDataModel {

    private Calendar dataVcto;

    private String provider;

    private String subCategory;

    private Double value;

    private TypeOfLaunch typeOfLaunch;

    public PredictionDashboardDataModel(Calendar dataVcto, String provider, String subCategory, Double value, TypeOfLaunch typeOfLaunch) {
        this.dataVcto = dataVcto;
        this.provider = provider;
        this.subCategory = subCategory;
        this.value = value;
        this.typeOfLaunch = typeOfLaunch;
    }

    public Calendar getDataVcto() {
        return dataVcto;
    }

    public void setDataVcto(Calendar dataVcto) {
        this.dataVcto = dataVcto;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public TypeOfLaunch getTypeOfLaunch() {
        return typeOfLaunch;
    }

    public void setTypeOfLaunch(TypeOfLaunch typeOfLaunch) {
        this.typeOfLaunch = typeOfLaunch;
    }
}
