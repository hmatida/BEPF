package br.com.personal_financee.pf.models.report_models;

public class RecipeAndExpenses {

    private String date;
    private Double recipe;
    private Double expenses;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getRecipe() {
        return recipe;
    }

    public void setRecipe(Double recipe) {
        this.recipe = recipe;
    }

    public Double getExpenses() {
        return expenses;
    }

    public void setExpenses(Double expenses) {
        this.expenses = expenses;
    }
}
