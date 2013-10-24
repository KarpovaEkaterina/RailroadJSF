package ru.tsystems.karpova.beans;

import ru.tsystems.karpova.service.RegistrationService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ViewScoped
@ManagedBean(name = "registrationBean")
public class RegistrationBean implements Serializable {

    @EJB
    private RegistrationService registrationService;
    @ManagedProperty(value = "#{currentUserBean}")
    private CurrentUserBean currentUserBean;

    private int accessLevel = -1;
    private String login = "";
    private String password = "";
    private String errorMessage = "";
    public static final int ACCESS_LEVEL_ADMIN = 3;
    public static final int ACCESS_LEVEL_PASSENGER = 1;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public String registration() {
        errorMessage = registrationService.checkUser(login, password);
        if ("".equals(errorMessage)) {
            if (registrationService.registration(login, password, currentUserBean.getAccessLevel())) {
                if (currentUserBean.getAccessLevel() == ACCESS_LEVEL_ADMIN) {
                    return "result_admin_page.xhtml?faces-redirect=true";
                } else {
                    currentUserBean.setLogin(login);
                    currentUserBean.setAccessLevel(ACCESS_LEVEL_PASSENGER);
                    return "passenger_page.xhtml?faces-redirect=true";
                }
            }
            errorMessage = "Server error";
            return "";
        }
        return "";
    }

    public CurrentUserBean getCurrentUserBean() {
        return currentUserBean;
    }

    public void setCurrentUserBean(CurrentUserBean currentUserBean) {
        this.currentUserBean = currentUserBean;
    }
}
