package br.com.personal_financee.pf.security;

import br.com.personal_financee.pf.models.Users;

public class CurrentUser {

    private String token;
    private Users users;

    public CurrentUser(String token, Users users) {
        this.token = token;
        this.users = users;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
