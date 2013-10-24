package ru.tsystems.karpova.dao;

import org.apache.log4j.Logger;
import ru.tsystems.karpova.entities.User;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class UserDAO extends BasicDAO {

    private static final Logger log = Logger.getLogger(UserDAO.class);

    public boolean saveUser(User user) {
        log.debug("Start saveUser");
        em.persist(user);
        log.debug("Saving user");
        return true;
    }

    public User loadUserByLogin(String login) {
        log.debug("Start loadUserByLogin select");
        List results = em.createQuery("select u from User u where u.login= :login").setParameter("login", login).getResultList();
        return results == null || results.isEmpty() ? null : (User) results.get(0);
    }
}
