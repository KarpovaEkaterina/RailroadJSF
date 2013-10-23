package ru.tsystems.karpova.service;

import org.apache.log4j.Logger;
import ru.tsystems.karpova.dao.UserDAO;
import ru.tsystems.karpova.entities.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class RegistrationService {

    private static Logger log = Logger.getLogger(RegistrationService.class);

    @EJB
    private UserDAO userDAO;
    public static final int ACCESS_LEVEL_PASSENGER = 1;
    public static final int ACCESS_LEVEL_MANAGER = 2;
    public static final int ACCESS_LEVEL_ADMIN = 3;

    public String checkUser(String login, String password) {
        String errorMessage = "";
        log.debug("Start \"registration\" method");
        log.info(login + " is trying to registration");
        if ("".equals(login) || ("".equals(password))) {
            errorMessage = "Необходимо заполнить все поля";
            return errorMessage;
        }
        User user = userDAO.loadUserByLogin(login);
        if (user != null) {
            log.info("Registration error. Duplicated login.");
            log.debug("Send RegistrationRespondInfo to client with DUPLICATED_LOGIN_STATUS");
            errorMessage = "Пользователь с таким логином уже существует";
            return errorMessage;
        }
        return errorMessage;
    }

    public boolean registration(String login, String password, int currentAccessLevel) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        if (currentAccessLevel == ACCESS_LEVEL_ADMIN) {
            user.setAccessLevel(ACCESS_LEVEL_MANAGER);
        } else {
            user.setAccessLevel(ACCESS_LEVEL_PASSENGER);
        }
        if (userDAO.saveUser(user)) {
            log.info("Registration passed");
            log.debug("Send RegistrationRespondInfo to client with OK_STATUS");
            return true;
        } else {
            log.debug("Send RegistrationRespondInfo to client with SERVER_ERROR_STATUS");
            return false;
        }
    }
}
