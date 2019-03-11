package br.com.personal_financee.pf.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;

@Entity
public class Provider {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_provider;

    private String name_provider;

    @ManyToOne
    @JoinColumn(name = "id_subCategory", referencedColumnName = "id_subCategory")
    private SubCategory subCategory;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "id_category", referencedColumnName = "id_category")
    private Category category;

    public Provider() {
    }

    public Provider(Long id_provider, String name_provider, SubCategory subCategory, Category category, Users user) {
        this.id_provider = id_provider;
        this.name_provider = name_provider;
        this.subCategory = subCategory;
        this.category = category;
        this.user=user;
    }

    public String getName_provider() {
        return name_provider;
    }

    public void setName_provider(String name_provider) {
        this.name_provider = name_provider;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public Long getId_provider() {
        return id_provider;
    }

    public void setId_provider(Long id_provider) {
        this.id_provider = id_provider;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
