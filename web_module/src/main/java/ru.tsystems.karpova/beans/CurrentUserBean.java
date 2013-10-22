package main.java.ru.tsystems.karpova.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@SessionScoped
@ManagedBean(name = "currentUserBean")
public class CurrentUserBean {

    private String login = "";
    private int accessLevel = 0;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }
}
