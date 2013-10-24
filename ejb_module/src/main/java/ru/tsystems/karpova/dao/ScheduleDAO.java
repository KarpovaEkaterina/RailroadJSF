package ru.tsystems.karpova.dao;

import org.apache.log4j.Logger;
import ru.tsystems.karpova.entities.Schedule;

import javax.ejb.Stateless;

@Stateless
public class ScheduleDAO extends BasicDAO {

    private static final Logger log = Logger.getLogger(ScheduleDAO.class);

    public boolean saveSchedule(Schedule schedule) {
        log.debug("Start saveSchedule");
        em.persist(schedule);
        return true;
    }
}
