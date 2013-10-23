package ru.tsystems.karpova.beans;

import ru.tsystems.karpova.service.AuthorizationService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@SessionScoped
@ManagedBean(name = "authorizationBean")
public class AuthorizationBean {

    @EJB
    private AuthorizationService authorizationService;
    private CurrentUserBean currentUserBean;

    private String userName = null;
    private String password = null;
    private int accessLevel = 0;
    private String errorMessage = "";
    public static final int ACCESS_LEVEL_GUEST = 0;
    public static final int ACCESS_LEVEL_MANAGER = 2;
    public static final int ACCESS_LEVEL_ADMIN = 3;

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "authorization.xhtml?faces-redirect=true";
    }

    public String login(){
        errorMessage = "";
         currentUserBean.setAccessLevel(authorizationService.login(userName, password));
        if (currentUserBean.getAccessLevel() == ACCESS_LEVEL_GUEST) {
            errorMessage = "Неправильный логин или пароль";
            return "";
        } else if (currentUserBean.getAccessLevel() == ACCESS_LEVEL_ADMIN) {
            currentUserBean.setLogin(userName);
            return "admin_page.xhtml?faces-redirect=true";
        } else if (currentUserBean.getAccessLevel() == ACCESS_LEVEL_MANAGER) {
            currentUserBean.setLogin(userName);
            return "manager_page.xhtml?faces-redirect=true";
        } else {
            currentUserBean.setLogin(userName);
            return "passenger_page.xhtml?faces-redirect=true";
        }
    }

    public void setCurrentUserBean(CurrentUserBean currentUserBean) {
        this.currentUserBean = currentUserBean;
    }
}
