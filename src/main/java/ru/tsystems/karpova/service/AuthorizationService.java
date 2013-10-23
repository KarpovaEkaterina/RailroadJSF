package ru.tsystems.karpova.service;

import ru.tsystems.karpova.dao.UserDAO;
import ru.tsystems.karpova.entities.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class AuthorizationService {

    //private static Logger log = Logger.getLogger(AuthorizationService.class);

    @EJB
    UserDAO userDAO;

    public int login(String login, String password) {
        //log.debug("Start \"login\" method");
        //log.info(login + " is trying to authorize");
        User user = userDAO.loadUserByLogin(login);
        if (user == null || user.getPassword() == null || !user.getPassword().equals(password)) {
            //log.info("Authorization error.");
            //log.debug("Send AuthorizationRespondInfo to client with WRONG_CREDENTIALS_STATUS");
            return 0;
        }
        //log.info("Auth passed");
        //log.debug("Send AuthorizationRespondInfo to client with OK_STATUS");
        return user.getAccessLevel();
    }
}
