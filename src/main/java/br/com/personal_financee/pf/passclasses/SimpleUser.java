package br.com.personal_financee.pf.passclasses;

import br.com.personal_financee.pf.models.Sexo;

public class SimpleUser {

    private Long id_usuario;

    private String name;

    private String login;

    private Sexo sex;

    private Boolean isActive;

    public SimpleUser(Long id_usuario, String name, String login, Sexo sex, Boolean isActive) {
        this.id_usuario = id_usuario;
        this.name = name;
        this.login = login;
        this.sex = sex;
        this.isActive = isActive;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Sexo getSex() {
        return sex;
    }

    public void setSex(Sexo sex) {
        this.sex = sex;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
