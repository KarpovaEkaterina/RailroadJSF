package ru.tsystems.karpova.dao;

import org.apache.log4j.Logger;
import ru.tsystems.karpova.entities.Station;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class StationDAO extends BasicDAO {

    private static final Logger log = Logger.getLogger(StationDAO.class);

    public List<Station> getAllStation() {
        log.debug("Start getAllStation select");
        List<Station> results = em.createQuery("select s from Station s").getResultList();
        return results;
    }

    public Station loadStationByName(String name) {
        log.debug("Start loadStationByName select");
        List results = em.createQuery("select s from Station s where s.name = :name").setParameter("name", name).getResultList();
        return results == null || results.isEmpty() ? null : (Station) results.get(0);
    }

    public boolean saveStation(Station station) {
        log.debug("Start saveStation");
        em.persist(station);
        return true;
    }
}
