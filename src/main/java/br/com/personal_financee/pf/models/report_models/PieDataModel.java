package br.com.personal_financee.pf.models.report_models;

import br.com.personal_financee.pf.models.SubCategory;

public class PieDataModel {

    private String subCategory;

    private Double value;

    private String color;

    public PieDataModel(String subCategory, Double value) {
        this.subCategory = subCategory;
        this.value = value;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
