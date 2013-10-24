package ru.tsystems.karpova.dao;

import org.apache.log4j.Logger;
import ru.tsystems.karpova.entities.Passenger;
import ru.tsystems.karpova.entities.Station;
import ru.tsystems.karpova.entities.Train;

import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Stateless
public class TrainDAO extends BasicDAO {

    private static Logger log = Logger.getLogger(TrainDAO.class);

    public Train loadTrain(String trainName) {
        log.debug("Start loadTrain select");
        List results = em.createQuery("select t from Train t where t.name = :name ")
                .setParameter("name", trainName)
                .getResultList();
        return results == null || results.isEmpty() ? null : (Train) results.get(0);
    }

    public List findTrain(String stationFrom, String stationTo, Date dateFrom, Date dateTo) {
        log.debug("Start findTrain select");
        List results = em.createNativeQuery("select train.name,\n" +
                "FROM_UNIXTIME(\n" +
                "UNIX_TIMESTAMP(train.departure)\n" +
                "+ SUM(\n" +
                "case\n" +
                "when wayTime.id = wayA.id\n" +
                "then 0\n" +
                "else UNIX_TIMESTAMP(wayTime.time)\n" +
                "end\n" +
                ") ) as startTime\n" +
                "from train\n" +
                "inner join route on train.id_route = route.id\n" +
                "inner join schedule as scheduleA on route.id = scheduleA.id_route\n" +
                "inner join way as wayA on scheduleA.id_way = wayA.id\n" +
                "inner join station as stationA on wayA.id_station1 = stationA.id\n" +
                "inner join schedule as scheduleB on route.id = scheduleB.id_route\n" +
                "inner join way as wayB on scheduleB.id_way = wayB.id\n" +
                "inner join station as stationB on wayB.id_station2 = stationB.id\n" +
                "inner join schedule as scheduleTime on route.id = scheduleTime.id_route\n" +
                "inner join way as wayTime on scheduleTime.id_way = wayTime.id\n" +
                "where stationA.name = ?\n" +
                "and stationB.name = ?\n" +
                "and scheduleA.seq_number <= scheduleB.seq_number\n" +
                "and scheduleTime.seq_number <= scheduleA.seq_number\n" +
                "group by\n" +
                "train.name\n" +
                "having startTime > ? \n" +
                "and startTime < ? ")
                .setParameter(1, stationFrom)
                .setParameter(2, stationTo)
                .setParameter(3, dateFrom)
                .setParameter(4, dateTo)
                .getResultList();
        return results;

    }

    public List<Object[]> findTrainByStationTo(String station) {
        log.debug("Start findTrainByStation select");
        List results = em.createNativeQuery("select train.name, \n" +
                "FROM_UNIXTIME(UNIX_TIMESTAMP(train.departure) + SUM(UNIX_TIMESTAMP(wayTime.time))) as startTime \n" +
                "from train \n" +
                "join route on train.id_route = route.id \n" +
                "join schedule as scheduleA on route.id = scheduleA.id_route \n" +
                "join way as wayA on scheduleA.id_way = wayA.id \n" +
                "join station as stationA on wayA.id_station2 = stationA.id \n" +
                "join schedule as scheduleTime on route.id = scheduleTime.id_route \n" +
                "join  way as wayTime on scheduleTime.id_way = wayTime.id \n" +
                "where stationA.name = ? \n" +
                "and scheduleTime.seq_number <= scheduleA.seq_number \n" +
                "group by train.name")
                .setParameter(1, station)
                .getResultList();
        return results;
    }

    public List<Object[]> findTrainByStationFrom(String station) {
        log.debug("Start findTrainByStation select");
        List results = em.createNativeQuery("select train.name, \n" +
                "FROM_UNIXTIME(UNIX_TIMESTAMP(train.departure) + SUM(case when wayTime.id = wayA.id then 0 else UNIX_TIMESTAMP(wayTime.time) end)) as startTime \n" +
                "from train \n" +
                "join route on train.id_route = route.id \n" +
                "join schedule as scheduleA on route.id = scheduleA.id_route \n" +
                "join way as wayA on scheduleA.id_way = wayA.id \n" +
                "join station as stationA on wayA.id_station1 = stationA.id \n" +
                "join schedule as scheduleTime on route.id = scheduleTime.id_route \n" +
                "join  way as wayTime on scheduleTime.id_way = wayTime.id \n" +
                "where stationA.name = ? \n" +
                "and scheduleTime.seq_number <= scheduleA.seq_number \n" +
                "group by train.name")
                .setParameter(1, station)
                .getResultList();
        return results;
    }

    public boolean checkDepartureTime(Train train, Station stationFrom) {
        log.debug("Start checkDepartureTime select");
        List results = em.createNativeQuery("select current_timestamp, \n" +
                "FROM_UNIXTIME(UNIX_TIMESTAMP(train.departure) + SUM(case when wayTime.id = wayA.id then 0 else UNIX_TIMESTAMP(wayTime.time) end)) as departureTime \n" +
                "from train \n" +
                "join route on train.id_route = route.id \n" +
                "join schedule as scheduleA on route.id = scheduleA.id_route \n" +
                "join way as wayA on scheduleA.id_way = wayA.id \n" +
                "join station as stationA on wayA.id_station1 = stationA.id \n" +
                "join schedule as scheduleTime on route.id = scheduleTime.id_route \n" +
                "join way as wayTime on scheduleTime.id_way = wayTime.id \n" +
                "where stationA.name = ? \n" +
                "and train.name = ? \n" +
                "and scheduleTime.seq_number <= scheduleA.seq_number \n" +
                "group by train.name")
                .setParameter(1, stationFrom.getName())
                .setParameter(2, train.getName())
                .getResultList();
        if (results.size() <= 0) {
            log.debug("Query in checkDepartureTime returned no rows.");
            return false;
        } else {
            log.debug("Query in checkDepartureTime returned current time: " + ((Object[]) results.get(0))[0]
                    + "and departure time: " + ((Object[]) results.get(0))[1]);
            Timestamp current = (Timestamp) ((Object[]) results.get(0))[0];
            current.setTime(current.getTime() + 10 * 60 * 1000);
            Timestamp departure = (Timestamp) ((Object[]) results.get(0))[1];
            return current.before(departure);
        }
    }

    public boolean isAlreadyExistPassengerOnTrain(Train train, Passenger passenger) {
        log.debug("Start isAlreadyExistPassengerOnTrain select");
        List results = em.createQuery("select count(train) " +
                "from Train train " +
                "join train.ticketsById ticket " +
                "join ticket.passengerByIdPassenger passenger " +
                "where train.name = :name " +
                "and passenger.firstname = :fName " +
                "and passenger.lastname = :lName " +
                "and passenger.birthday = :bDay")
                .setParameter("name", train.getName())
                .setParameter("fName", passenger.getFirstname())
                .setParameter("lName", passenger.getLastname())
                .setParameter("bDay", passenger.getBirthday())
                .getResultList();
        return (Long) results.get(0) != 0;
    }

    public HashMap<Integer, Integer[]> countOfPassengerOnEveryStation(Train train) {
        log.debug("Start countOfPassengerOnEveryStation select");
        List stationFrom = em.createQuery("select station.id, 0, count(ticket.id), schedule.seqNumber " +
                "from Train train " +
                "left join train.ticketsById ticket " +
                "inner join ticket.stationByStationFrom station " +
                "inner join train.routeByIdRoute route " +
                "inner join route.schedulesById schedule " +
                "inner join schedule.wayByIdWay way " +
                "inner join way.stationByIdStation1 stationFrom " +
                "where train.name = :name " +
                "and stationFrom.id = station.id " +
                " group by station.id")
                .setParameter("name", train.getName())
                .getResultList();
        List stationTo = em.createQuery("select station.id, 0, count(ticket.id), schedule.seqNumber " +
                "from Train train " +
                "left join train.ticketsById ticket " +
                "inner join ticket.stationByStationTo station " +
                "inner join train.routeByIdRoute route " +
                "inner join route.schedulesById schedule " +
                "inner join schedule.wayByIdWay way " +
                "inner join way.stationByIdStation2 stationTo " +
                "where train.name = :name " +
                "and stationTo.id = station.id " +
                " group by station.id")
                .setParameter("name", train.getName())
                .getResultList();
        HashMap<Integer, Integer[]> result = new HashMap<Integer, Integer[]>();
        for (Object[] obj : (List<Object[]>) stationFrom) {
            if (result.containsKey(obj[0])) {
                Integer[] a = result.get(obj[0]);
                a[0] += ((Long) obj[2]).intValue();
                result.put((Integer) obj[0], a);
            } else {
                result.put((Integer) obj[0], new Integer[]{((Long) obj[2]).intValue(), ((Long) obj[1]).intValue(), ((Integer) obj[3])-1});
            }
        }
        for (Object[] obj : (List<Object[]>) stationTo) {
            if (result.containsKey(obj[0])) {
                Integer[] a = result.get(obj[0]);
                a[1] += ((Long) obj[2]).intValue();
                result.put((Integer) obj[0], a);
            } else {
                result.put((Integer) obj[0], new Integer[]{((Long) obj[1]).intValue(), ((Long) obj[2]).intValue(), (Integer) obj[3]});
            }
        }
        return result;
    }

    public List getAllStationsByTrain(Train train) {
        return getAllStationsByTrainName(train.getName());
    }

    public List getAllStationsByTrainName(String train) {
        log.debug("Start getAllStationsByTrain select");
        List results = em.createNativeQuery("select distinct station.id, station.name, \n" +
                "case station.id\n" +
                "when  way.id_station1 \n" +
                "then schedule.seq_number - 1 \n" +
                "else schedule.seq_number\n" +
                "end as seq\n" +
                "from station\n" +
                "inner join way on (station.id = way.id_station1 or station.id = way.id_station2)\n" +
                "inner join schedule on schedule.id_way = way.id\n" +
                "inner join route on schedule.id_route = route.id\n" +
                "inner join train on route.id = train.id_route\n" +
                "where train.name =?\n" +
                "order by seq")
                .setParameter(1, train)
                .getResultList();
        return results;
    }

    public boolean saveTrain(Train train) {
        log.debug("Start saveTrain");
        em.persist(train);
        log.debug("Saving train");
        return true;
    }

    public List<Train> getAllTrains() {
        log.debug("Start getAllTrains select");
        List<Train> results = em.createQuery("select t from Train t").getResultList();
        return results;

    }
}