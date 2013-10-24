package ru.tsystems.karpova.dao;

import org.apache.log4j.Logger;
import ru.tsystems.karpova.entities.Way;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class WayDAO extends BasicDAO {

    private static final Logger log = Logger.getLogger(WayDAO.class);

    public List<Object[]> getAllWays() {
        log.debug("Start getAllWays select");
        List<Object[]> results = em.createQuery("select stationA.name, stationB.name, way.time, way.price " +
                "from Way way " +
                "inner join way.stationByIdStation1 stationA " +
                "inner join way.stationByIdStation2 stationB")
                .getResultList();
        return results;
    }

    public boolean saveWay(Way way) {
        log.debug("Start saveWay");
        em.persist(way);
        log.debug("Saving way");
        return true;
    }

    public Way loadWayByStations(String stationA, String stationB) {
        log.debug("Start loadWayByStations select");
        List results = em.createQuery("select way " +
                "from Way way " +
                "inner join way.stationByIdStation1 stationA " +
                "inner join way.stationByIdStation2 stationB " +
                "where stationA.name = :nameA " +
                "and stationB.name = :nameB")
                .setParameter("nameA", stationA)
                .setParameter("nameB", stationB)
                .getResultList();
        return results == null || results.isEmpty() ? null : (Way) results.get(0);
    }
}
