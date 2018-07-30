package br.com.personal_financee.pf.security;

import java.io.Serializable;

public class JwtAuthenticationRequest {

    private String login;
    private String password;

    public JwtAuthenticationRequest() {
    }

    public JwtAuthenticationRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
