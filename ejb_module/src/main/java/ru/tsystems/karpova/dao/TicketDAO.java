package ru.tsystems.karpova.dao;

import org.apache.log4j.Logger;
import ru.tsystems.karpova.entities.Ticket;

import javax.ejb.Stateless;

@Stateless
public class TicketDAO extends BasicDAO {

    private static final Logger log = Logger.getLogger(TicketDAO.class);

    public boolean saveTicket(Ticket ticket) {
        log.debug("Start saveTicket");
        em.persist(ticket);
        log.debug("Saving ticket");
        return true;
    }
}
