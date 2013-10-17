package ru.tsystems.karpova.beans;

public class AuthorizationBean {
    private String login = null;
    private String password = null;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
