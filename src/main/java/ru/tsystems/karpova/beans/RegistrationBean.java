package ru.tsystems.karpova.beans;

public class RegistrationBean extends AuthorizationBean {

    private int accessLevel = -1;

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public int getAccessLevel() {
        return accessLevel;
    }
}
