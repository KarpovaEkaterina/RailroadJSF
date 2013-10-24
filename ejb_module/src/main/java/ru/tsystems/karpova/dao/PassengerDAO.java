package ru.tsystems.karpova.dao;

import org.apache.log4j.Logger;
import ru.tsystems.karpova.entities.Passenger;
import ru.tsystems.karpova.entities.Train;

import javax.ejb.Stateless;
import java.util.Date;
import java.util.List;

@Stateless
public class PassengerDAO extends BasicDAO {

    private static final Logger log = Logger.getLogger(PassengerDAO.class);

  /*  public boolean isAlreadyExistPassenger(Passenger passenger) {
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
    }*/

    public boolean savePassenger(Passenger passenger) {
        log.debug("Start savePassenger");
        em.persist(passenger);
        log.debug("Saving passenger");
        return true;
    }

    public Passenger loadPassenger(String firstname, String lastname, Date birthday) {
        log.debug("Start loadPassenger select");
        List results = em.createQuery("select p from Passenger p " +
                "where p.firstname = :firstname and p.lastname = :lastname and p.birthday = :birthday")
                .setParameter("firstname", firstname)
                .setParameter("lastname", lastname)
                .setParameter("birthday", birthday)
                .getResultList();
        return results == null || results.isEmpty() ? null : (Passenger) results.get(0);
    }

    public List<Passenger> getAllPassengerByTrain(Train train) {
        log.debug("Start getAllPassengerByTrain select");
        List<Passenger> results = em.createQuery("select passenger " +
                "from Train train " +
                "inner join train.ticketsById ticket " +
                "inner join ticket.passengerByIdPassenger passenger " +
                "where train.name = :name")
                .setParameter("name", train.getName())
                .getResultList();
        return results;

    }
}