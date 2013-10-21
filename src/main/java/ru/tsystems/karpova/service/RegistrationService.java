package ru.tsystems.karpova.service;

import org.apache.log4j.Logger;
import ru.tsystems.karpova.dao.UserDAO;
import ru.tsystems.karpova.entities.User;
import ru.tsystems.karpova.beans.RegistrationBean;

public class RegistrationService {

    private static Logger log = Logger.getLogger(RegistrationService.class);

    private UserDAO userDAO;
    private String errorMessage = "";
    private String errorVisibility = "invisible";
    private RegistrationBean regBean;
    private User user;
    public static final int ACCESS_LEVEL_PASSENGER = 1;
    public static final int ACCESS_LEVEL_MANAGER = 2;
    public static final int ACCESS_LEVEL_ADMIN = 3;

    public RegistrationService() {
        userDAO = new UserDAO();
    }

    public String registration() {
        log.debug("Start \"registration\" method");
        log.info(regBean.getLogin() + " is trying to registration");
        if (("".equals(regBean.getPassword())) || ("".equals(regBean.getLogin()))) {
            errorMessage = "Необходимо заполнить все поля";
            errorVisibility = "visible";
            return "";
        }
        User user = userDAO.loadUserByLogin(regBean.getLogin());
        if (user != null) {
            log.info("Registration error. Duplicated login.");
            log.debug("Send RegistrationRespondInfo to client with DUPLICATED_LOGIN_STATUS");
            errorMessage = "Пользователь с таким логином уже существует";
            errorVisibility = "visible";
            return "";
        }
        user = new User();
        user.setLogin(regBean.getLogin());
        user.setPassword(regBean.getPassword());
        if (this.user.getAccessLevel() == ACCESS_LEVEL_ADMIN) {
            user.setAccessLevel(ACCESS_LEVEL_MANAGER);
        } else {
            user.setAccessLevel(ACCESS_LEVEL_PASSENGER);
        }
        if (userDAO.saveUser(user)) {
            log.info("Registration passed");
            log.debug("Send RegistrationRespondInfo to client with OK_STATUS");
            if (this.user.getAccessLevel() == ACCESS_LEVEL_ADMIN) {
                return "result_admin_page.xhtml?faces-redirect=true";
            } else {
                this.user.setLogin(user.getLogin());
                this.user.setPassword(user.getPassword());
                this.user.setAccessLevel(user.getAccessLevel());
                return "passenger_page.xhtml?faces-redirect=true";
            }
        } else {
            log.debug("Send RegistrationRespondInfo to client with SERVER_ERROR_STATUS");
            errorMessage = "Server error";
            errorVisibility = "visible";
            return "";
        }
    }

    public void setRegBean(RegistrationBean regBean) {
        this.regBean = regBean;
    }

    public RegistrationBean getRegBean() {
        return regBean;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorVisibility() {
        return errorVisibility;
    }

    public void setErrorVisibility(String errorVisibility) {
        this.errorVisibility = errorVisibility;
    }
}
