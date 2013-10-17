package ru.tsystems.karpova.service;

import org.apache.log4j.Logger;
import ru.tsystems.karpova.dao.UserDAO;
import ru.tsystems.karpova.entities.User;
import ru.tsystems.karpova.beans.AuthorizationBean;

public class AuthorizationService {

    private static Logger log = Logger.getLogger(AuthorizationService.class);

    private UserDAO userDAO;
    private AuthorizationBean authBean;
    private String errorMessage = "";
    private String errorVisibility = "invisible";
    private User user;
    public static final int ACCESS_LEVEL_PASSENGER = 1;
    public static final int ACCESS_LEVEL_MANAGER = 2;
    public static final int ACCESS_LEVEL_ADMIN = 3;

    public AuthorizationService() {
        userDAO = new UserDAO();
    }

    public String login() {
        log.debug("Start \"login\" method");
        log.info(authBean.getLogin() + " is trying to authorize");
        User user = userDAO.loadUserByLogin(authBean.getLogin());
        if (user == null || user.getPassword() == null || !user.getPassword().equals(authBean.getPassword())) {
            log.info("Authorization error.");
            errorMessage = "Неправильный логин или пароль";
            errorVisibility = "visible";
            log.debug("Send AuthorizationRespondInfo to client with WRONG_CREDENTIALS_STATUS");
            return "";
        }
        log.info("Auth passed");
        log.debug("Send AuthorizationRespondInfo to client with OK_STATUS");
        this.user.setLogin(user.getLogin());
        this.user.setPassword(user.getPassword());
        this.user.setAccessLevel(user.getAccessLevel());
        if (user.getAccessLevel() == ACCESS_LEVEL_ADMIN) {
            return "admin_page.xhtml";
        } else if (user.getAccessLevel() == ACCESS_LEVEL_MANAGER) {
            return "manager_page.xhtml";
        }
        return "passenger_page.xhtml";
    }

    public void setAuthBean(AuthorizationBean authBean) {
        this.authBean = authBean;
    }

    public AuthorizationBean getAuthBean() {
        return authBean;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorVisibility() {
        return errorVisibility;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
