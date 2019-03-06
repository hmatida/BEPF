package br.com.personal_financee.pf.models;

import javax.persistence.*;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_category;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Users users;

    private String name_category;

    private TypeOfLaunch typeOfLaunch;

    public Long getId_category() {
        return id_category;
    }

    public void setId_category(Long id_category) {
        this.id_category = id_category;
    }

    public String getName_category() {
        return name_category;
    }

    public void setName_category(String name_category) {
        this.name_category = name_category;
    }

    public TypeOfLaunch getTypeOfLaunch() {
        return typeOfLaunch;
    }

    public void setTypeOfLaunch(TypeOfLaunch typeOfLaunch) {
        this.typeOfLaunch = typeOfLaunch;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
