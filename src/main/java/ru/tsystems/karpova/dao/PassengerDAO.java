package ru.tsystems.karpova.dao;

import org.apache.log4j.Logger;
import ru.tsystems.karpova.entities.Passenger;
import ru.tsystems.karpova.entities.Train;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;
import java.util.Date;
import java.util.List;

public class PassengerDAO extends BasicDAO{

    private static Logger log = Logger.getLogger(PassengerDAO.class);

    public boolean isAlreadyExistPassenger(Passenger passenger) {
        log.debug("Start isAlreadyExistPassenger select");
        List results = em.createQuery("select count(*)\n" +
                "from Passenger passenger \n" +
                "where passenger.firstname = ?\n" +
                "and passenger.lastname = ?\n" +
                "and passenger.birthday = ?")
                .setParameter(1, passenger.getFirstname())
                .setParameter(2, passenger.getLastname())
                .setParameter(3, passenger.getBirthday())
                .getResultList();
        return (Long) results.get(0) != 0;
    }

    public boolean savePassenger(Passenger passenger) {
        log.debug("Start savePassenger");
        EntityManager em = emf.createEntityManager();
        EntityTransaction trx = em.getTransaction();
        try {
            trx.begin();

            em.persist(passenger);

            trx.commit();
            log.debug("Saving passenger");
            return true;
        } catch (RollbackException e) {
            log.error("Can't save passenger", e);
            trx.rollback();
            return false;
        }
    }

    public Passenger loadPassenger(String firstname, String lastname, Date birthday) {
        log.debug("Start loadPassenger select");
        List results = em.createQuery("from Passenger where firstname = ? and lastname = ? and birthday = ?")
                .setParameter(1, firstname)
                .setParameter(2, lastname)
                .setParameter(3, birthday)
                .getResultList();
        return results == null || results.isEmpty() ? null : (Passenger) results.get(0);
    }

    public List<Passenger> getAllPassengerByTrain(Train train) {
        log.debug("Start getAllPassengerByTrain select");
        List<Passenger> results = em.createQuery("select passenger\n" +
                "from Train train\n" +
                "inner join train.ticketsById ticket\n" +
                "inner join ticket.passengerByIdPassenger passenger\n" +
                "where train.name = ?")
                .setParameter(1, train.getName())
                .getResultList();
        return results;

    }
}