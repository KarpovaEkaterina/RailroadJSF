package ru.tsystems.karpova.service;

import org.apache.log4j.Logger;
import ru.tsystems.karpova.dao.PassengerDAO;
import ru.tsystems.karpova.dao.RouteDAO;
import ru.tsystems.karpova.dao.TrainDAO;
import ru.tsystems.karpova.entities.Passenger;
import ru.tsystems.karpova.entities.Route;
import ru.tsystems.karpova.entities.Train;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
public class TrainService {

    private static final Logger log = Logger.getLogger(TrainService.class);

    @EJB
    private TrainDAO trainDAO;
    @EJB
    private PassengerDAO passengerDAO;
    @EJB
    private RouteDAO routeDAO;

    public List<Object[]> viewPassengerByTrain(String trainName) throws IOException {
        List<Object[]> allPassengerByTrain = new ArrayList<Object[]>();
        log.debug("Start method \"viewPassengerByTrain\"");
        allPassengerByTrain.clear();

        Train train = trainDAO.loadTrain(trainName);
        if (train == null) {
            log.debug("Send ViewPassengerByTrainRespondInfo to client with WRONG_TRAIN_NAME_STATUS");
            return allPassengerByTrain;
        }
        List<Passenger> allPassengerByTrainList = passengerDAO.getAllPassengerByTrain(train);
        for (Passenger passenger : allPassengerByTrainList) {
            allPassengerByTrain.add(new Object[]{passenger.getFirstname(), passenger.getLastname(), passenger.getBirthday()});
        }
        log.debug("Send ViewPassengerByTrainRespondInfo to client");
        return allPassengerByTrain;
    }

    public List<Object[]> viewAllTrains() throws IOException {
        log.debug("Start method \"viewAllTrains\"");
        List<Train> allTrainsList = trainDAO.getAllTrains();
        List<Object[]> allTrains = new ArrayList<Object[]>();
        for (Train train : allTrainsList) {
            allTrains.add(new Object[]{train.getName(), train.getTotalSeats(), train.getDeparture(), train.getRouteByIdRoute().getName()});
        }
        log.debug("Send GetAllTrainsRespondInfo to client");
        return allTrains;
    }

    public String addTrain(String trainName, String routeName, int totalSeats, Date departureTime) throws IOException {
        String message = "";
        log.debug("Start method \"addTrain\"");
        if ("".equals(routeName) || "".equals(trainName)
                || "".equals(totalSeats) || departureTime == null) {
            message = "Все поля должны быть заполнены";
            return message;
        }
        Route route = routeDAO.loadRoute(routeName);
        if (route == null) {
            log.debug("Send AddTrainRespondInfo to client with WRONG_ROUTE_NAME_STATUS");
            message = "Маршрут не найден";
            return message;
        }
        Date currentTime = new Date();
        if (!currentTime.before(departureTime)) {
            message = "Время отправления уже прошло";
            return message;
        }
        Train train = trainDAO.loadTrain(trainName);
        if (train != null) {
            log.debug("Send AddTrainRespondInfo to client with WRONG_TRAIN_NAME");
            message = "Поезд с таким названием уже существует";
            return message;
        }
        train = new Train(trainName, totalSeats,
                new Timestamp(departureTime.getTime()), route);
        if (!trainDAO.saveTrain(train)) {
            log.debug("Send AddTrainRespondInfo to client with SERVER_ERROR_STATUS");
            message = "Server error";
            return message;
        } else {
            log.debug("Send AddTrainRespondInfo to client with OK_STATUS");
            message = "Поезд добавлен";
            return message;
        }
    }

    public List<Object[]> scheduleByStation(String trainName, boolean typeSchedule) throws IOException {
        log.debug("Start method \"scheduleByStation\"");
        List<Object[]> trains;
        if (typeSchedule) {
            trains = trainDAO.findTrainByStationFrom(trainName);
        } else {
            trains = trainDAO.findTrainByStationTo(trainName);
        }

        log.debug("Send ScheduleRespondInfo to client");
        return trains;
    }

    public List<Object[]> findTrain(String stationFrom, String stationTo, Date dateFrom, Date dateTo) throws IOException {
        log.debug("Start method \"findTrain\"");
        List<Object[]> trains = trainDAO.findTrain(stationFrom,
                stationTo, dateFrom, dateTo);

        log.debug("Send FindTrainRespondInfo to client");
        return trains;
    }
}
