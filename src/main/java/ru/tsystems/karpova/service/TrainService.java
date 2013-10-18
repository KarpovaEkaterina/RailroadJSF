package ru.tsystems.karpova.service;

import org.apache.log4j.Logger;
import ru.tsystems.karpova.dao.PassengerDAO;
import ru.tsystems.karpova.dao.RouteDAO;
import ru.tsystems.karpova.dao.TrainDAO;
import ru.tsystems.karpova.entities.Passenger;
import ru.tsystems.karpova.entities.Route;
import ru.tsystems.karpova.entities.Train;
import ru.tsystems.karpova.beans.AddTrainBean;
import ru.tsystems.karpova.beans.FindTrainBean;
import ru.tsystems.karpova.beans.ScheduleBean;
import ru.tsystems.karpova.beans.ViewPassengerByTrainBean;
import ru.tsystems.karpova.entities.User;
import ru.tsystems.karpova.respond.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TrainService {

    private static Logger log = Logger.getLogger(TrainService.class);

    private TrainDAO trainDAO;
    private PassengerDAO passengerDAO;
    private RouteDAO routeDAO;
    private FindTrainBean findTrBean;
    private User user;
    private ViewPassengerByTrainBean viewPassengerBean;
    private List<Object[]> resultFindTrains = new ArrayList<Object[]>();
    private ScheduleBean schBean;
    private AddTrainBean addTrBean;
    private String message = "";
    private boolean typeSchedule = true;

    public TrainService() {
        trainDAO = new TrainDAO();
        passengerDAO = new PassengerDAO();
        routeDAO = new RouteDAO();
    }

    public ru.tsystems.karpova.respond.ViewPassengerByTrainRespondInfo viewPassengerByTrain() throws IOException {
        log.debug("Start method \"viewPassengerByTrain\"");
        Train train = trainDAO.loadTrain(viewPassengerBean.getTrainName());
        if (train == null) {
            ViewPassengerByTrainRespondInfo respond = new ViewPassengerByTrainRespondInfo(ViewPassengerByTrainRespondInfo.WRONG_TRAIN_NAME_STATUS);
            log.debug("Send ViewPassengerByTrainRespondInfo to client with WRONG_TRAIN_NAME_STATUS");
            return respond;
        }
        List<Passenger> allPassengerByTrainList = passengerDAO.getAllPassengerByTrain(train);
        List<Object[]> allPassengerByTrain = new ArrayList<Object[]>();
        for (Passenger passenger : allPassengerByTrainList) {
            allPassengerByTrain.add(new Object[]{passenger.getFirstname(), passenger.getLastname(), passenger.getBirthday()});
        }
        ViewPassengerByTrainRespondInfo respond = new ViewPassengerByTrainRespondInfo(allPassengerByTrain);
        log.debug("Send ViewPassengerByTrainRespondInfo to client");
        return respond;
    }

    public GetAllTrainsRespondInfo getAllTrains() throws IOException {
        log.debug("Start method \"getAllTrains\"");
        List<Train> allTrainsList = trainDAO.getAllTrains();
        List<Object[]> allTrains = new ArrayList<Object[]>();
        for (Train train : allTrainsList) {
            allTrains.add(new Object[]{train.getName(), train.getTotalSeats(), train.getDeparture(), train.getRouteByIdRoute().getName()});
        }
        GetAllTrainsRespondInfo respond = new GetAllTrainsRespondInfo(allTrains);
        log.debug("Send GetAllTrainsRespondInfo to client");
        return respond;
    }

    public String addTrain() throws IOException {
        log.debug("Start method \"addTrain\"");
        if ("".equals(addTrBean.getRoute()) || "".equals(addTrBean.getTrainName())
                || "".equals(addTrBean.getTotalSeats()) || addTrBean.getDepartureTime() == null) {
            message = "Все поля должны быть заполнены";
            return message;
        }
        Route route = routeDAO.loadRoute(addTrBean.getRoute());
        if (route == null) {
            log.debug("Send AddTrainRespondInfo to client with WRONG_ROUTE_NAME_STATUS");
            message = "Маршрут не найден";
            return message;
        }
        Train train = new Train(addTrBean.getTrainName(), addTrBean.getTotalSeats(),
                new Timestamp(addTrBean.getDepartureTime().getTime()), route);
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

    public List<Object[]> scheduleByStation() throws IOException {
        log.debug("Start method \"scheduleByStation\"");
        List<Object[]> trains = new ArrayList<Object[]>();
        if (typeSchedule) {
            trains = trainDAO.findTrainByStationFrom(schBean.getStation());
        } else {
            trains = trainDAO.findTrainByStationTo(schBean.getStation());
        }

        log.debug("Send ScheduleRespondInfo to client");
        setResultFindTrains(trains);
        return trains;
    }

    public List<Object[]> findTrain() throws IOException {
        log.debug("Start method \"findTrain\"");
        List<Object[]> trains = trainDAO.findTrain(findTrBean.getStationFrom(),
                findTrBean.getStationTo(), findTrBean.getDateFrom(), findTrBean.getDateTo());

        log.debug("Send FindTrainRespondInfo to client");
        setResultFindTrains(trains);
        return trains;
    }

    public void setFindTrBean(FindTrainBean findTrBean) {
        this.findTrBean = findTrBean;
    }

    public FindTrainBean getFindTrBean() {
        return findTrBean;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setViewPassengerBean(ViewPassengerByTrainBean viewPassengerBean) {
        this.viewPassengerBean = viewPassengerBean;
    }

    public ViewPassengerByTrainBean getViewPassengerBean() {
        return viewPassengerBean;
    }

    public List<Object[]> getResultFindTrains() {
        return resultFindTrains;
    }

    public void setResultFindTrains(List<Object[]> resultFindTrains) {
        this.resultFindTrains = resultFindTrains;
    }

    public void setSchBean(ScheduleBean schBean) {
        this.schBean = schBean;
    }

    public ScheduleBean getSchBean() {
        return schBean;
    }

    public void setAddTrBean(AddTrainBean addTrBean) {
        this.addTrBean = addTrBean;
    }

    public AddTrainBean getAddTrBean() {
        return addTrBean;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Integer> getTotalSeats() {
        List<Integer> totalSeats = new ArrayList<Integer>();
        for (int i = 1; i <= 100; i++) {
            totalSeats.add(i);
        }
        return totalSeats;
    }

    public boolean isTypeSchedule() {
        return typeSchedule;
    }

    public void setTypeSchedule(boolean typeSchedule) {
        this.typeSchedule = typeSchedule;
    }
}
