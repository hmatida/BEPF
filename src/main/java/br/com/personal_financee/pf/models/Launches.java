package br.com.personal_financee.pf.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Calendar;

@Entity
public class Launches {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_launch;

    @DateTimeFormat (pattern = "yyyy-MM-dd")
    private Calendar dt;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "id_provider", referencedColumnName = "id_provider")
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "id_account", referencedColumnName = "id_account")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "id_subCategory", referencedColumnName = "id_subCategory")
    private SubCategory subCategory;

    @ManyToOne
    @JoinColumn(name = "id_launchPrediction", referencedColumnName = "id_launchPrediction")
    private LaunchPrediction launchPrediction;

    private Integer chart = 1; // 0->Não entrar nos gráficos 1->Entrar nos gráficos.

    private String atach = null;

    private TypeOfLaunch typeOfLaunch;

    private String description;

    private Double discont;

    private Double interest;

    private Double tt;

    private Double pay_value;

    private Double balance;

    public Long getId_launch() {
        return id_launch;
    }

    public void setId_launch(Long id_launch) {
        this.id_launch = id_launch;
    }

    public Calendar getDt() {
        return dt;
    }

    public void setDt(Calendar dt) {
        this.dt = dt;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getDiscont() {
        return discont;
    }

    public void setDiscont(Double discont) {
        this.discont = discont;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getTt() {
        return tt;
    }

    public void setTt(Double tt) {
        this.tt = tt;
    }

    public Double getPay_value() {
        return pay_value;
    }

    public void setPay_value(Double pay_value) {
        this.pay_value = pay_value;
    }

    public LaunchPrediction getLaunchPrediction() {
        return launchPrediction;
    }

    public void setLaunchPrediction(LaunchPrediction launchPrediction) {
        this.launchPrediction = launchPrediction;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getAtach() {
        return atach;
    }

    public void setAtach(String atach) {
        this.atach = atach;
    }

    public Integer getChart() {
        return chart;
    }

    public void setChart(Integer chart) {
        this.chart = chart;
    }
}
