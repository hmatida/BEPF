package br.com.personal_financee.pf.models;

import javax.persistence.*;

@Entity
public class SubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_subCategory;

    @ManyToOne
    @JoinColumn(name = "id_category", referencedColumnName = "id_category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Users users;

    private String subCategoryName;

    public Long getId_subCategory() {
        return id_subCategory;
    }

    public void setId_subCategory(Long id_subCategory) {
        this.id_subCategory = id_subCategory;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
