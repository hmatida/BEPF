package br.com.personal_financee.pf.passclasses;

public class PassSubCategory {

    private Long id_subCategory;

    private Long category;

    private String subCategoryName;

    public Long getId_subCategory() {
        return id_subCategory;
    }

    public void setId_subCategory(Long id_subCategory) {
        this.id_subCategory = id_subCategory;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }
}
