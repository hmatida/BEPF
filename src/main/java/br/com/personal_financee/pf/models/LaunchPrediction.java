package br.com.personal_financee.pf.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Calendar;

@Entity
public class LaunchPrediction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_launchPrediction;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Users user;

    private String part;

    @ManyToOne
    @JoinColumn(name = "id_subCategory", referencedColumnName = "id_subCategory")
    private SubCategory subCategory;

    private TypeOfLaunch typeOfLaunch;

    @ManyToOne
    @JoinColumn(name = "id_provider", referencedColumnName = "id_provider")
    private Provider provider;

    private Double value;

    private String Description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar dt_exp;

    private Boolean isPay = false;

    public Long getId_launchPrediction() {
        return id_launchPrediction;
    }

    public void setId_launchPrediction(Long id_launchPrediction) {
        this.id_launchPrediction = id_launchPrediction;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public TypeOfLaunch getTypeOfLaunch() {
        return typeOfLaunch;
    }

    public void setTypeOfLaunch(TypeOfLaunch typeOfLaunch) {
        this.typeOfLaunch = typeOfLaunch;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Calendar getDt_exp() {
        return dt_exp;
    }

    public void setDt_exp(Calendar dt_exp) {
        this.dt_exp = dt_exp;
    }

    public Boolean getPay() {
        return isPay;
    }

    public void setPay(Boolean pay) {
        isPay = pay;
    }


    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
