package ru.tsystems.karpova.dao;

import org.apache.log4j.Logger;
import ru.tsystems.karpova.entities.Route;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class RouteDAO extends BasicDAO {

    private static final Logger log = Logger.getLogger(RouteDAO.class);

    public List<Route> getAllRoutes() {
        log.debug("Start getAllRoutes select");
        List<Route> results = em.createQuery("select r from Route r").getResultList();
        return results;
    }

    public Route loadRoute(String route) {
        log.debug("Start loadRoute select");
        List results = em.createQuery("select r from Route r where r.name = :name")
                .setParameter("name", route)
                .getResultList();
        return results == null || results.isEmpty() ? null : (Route) results.get(0);
    }

    public boolean saveRoute(Route route) {
        log.debug("Start saveRoute");
        em.persist(route);
        log.debug("Saving route");
        return true;
    }
}
