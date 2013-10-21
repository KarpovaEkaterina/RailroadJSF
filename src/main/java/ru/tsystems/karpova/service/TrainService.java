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

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrainService {

    private static Logger log = Logger.getLogger(TrainService.class);

    private TrainDAO trainDAO;
    private PassengerDAO passengerDAO;
    private RouteDAO routeDAO;
    private FindTrainBean findTrBean;
    private User user;
    private ViewPassengerByTrainBean viewPassengerBean;
    private List<Object[]> scheduleTrainsByStation = new ArrayList<Object[]>();
    private List<Object[]> viewAllTrains = new ArrayList<Object[]>();
    private ScheduleBean schBean;
    private AddTrainBean addTrBean;
    private String message = "";
    private boolean typeSchedule = true;
    private List<Object[]> allPassengerByTrain = new ArrayList<Object[]>();

    public List<Object[]> getAllPassengerByTrain() {
        return allPassengerByTrain;
    }

    public void setAllPassengerByTrain(List<Object[]> allPassengerByTrain) {
        this.allPassengerByTrain = allPassengerByTrain;
    }

    public TrainService() {
        trainDAO = new TrainDAO();
        passengerDAO = new PassengerDAO();

        routeDAO = new RouteDAO();
    }

    public void viewPassengerByTrain() throws IOException {
        log.debug("Start method \"viewPassengerByTrain\"");
        allPassengerByTrain.clear();

        Train train = trainDAO.loadTrain(viewPassengerBean.getTrainName());
        if (train == null) {
            log.debug("Send ViewPassengerByTrainRespondInfo to client with WRONG_TRAIN_NAME_STATUS");
            return;
        }
        List<Passenger> allPassengerByTrainList = passengerDAO.getAllPassengerByTrain(train);
        for (Passenger passenger : allPassengerByTrainList) {
            allPassengerByTrain.add(new Object[]{passenger.getFirstname(), passenger.getLastname(), passenger.getBirthday()});
        }
        log.debug("Send ViewPassengerByTrainRespondInfo to client");
        return;
    }

    public void viewAllTrains() throws IOException {
        log.debug("Start method \"viewAllTrains\"");
        List<Train> allTrainsList = trainDAO.getAllTrains();
        List<Object[]> allTrains = new ArrayList<Object[]>();
        for (Train train : allTrainsList) {
            allTrains.add(new Object[]{train.getName(), train.getTotalSeats(), train.getDeparture(), train.getRouteByIdRoute().getName()});
        }
        log.debug("Send GetAllTrainsRespondInfo to client");
        setViewAllTrains(allTrains);
        return;
    }

    public void addTrain() throws IOException {
        log.debug("Start method \"addTrain\"");
        if ("".equals(addTrBean.getRoute()) || "".equals(addTrBean.getTrainName())
                || "".equals(addTrBean.getTotalSeats()) || addTrBean.getDepartureTime() == null) {
            message = "Все поля должны быть заполнены";
            return;
        }
        Route route = routeDAO.loadRoute(addTrBean.getRoute());
        if (route == null) {
            log.debug("Send AddTrainRespondInfo to client with WRONG_ROUTE_NAME_STATUS");
            message = "Маршрут не найден";
            return;
        }
        Date currentTime = new Date();
        if(!currentTime.before(addTrBean.getDepartureTime())){
            message = "Время отправления уже прошло";
            return;
        }
        Train train = new Train(addTrBean.getTrainName(), addTrBean.getTotalSeats(),
                new Timestamp(addTrBean.getDepartureTime().getTime()), route);
        if (!trainDAO.saveTrain(train)) {
            log.debug("Send AddTrainRespondInfo to client with SERVER_ERROR_STATUS");
            message = "Server error";
            return;
        } else {
            log.debug("Send AddTrainRespondInfo to client with OK_STATUS");
            message = "Поезд добавлен";
            return;
        }
    }

    public void scheduleByStation() throws IOException {
        log.debug("Start method \"scheduleByStation\"");
        List<Object[]> trains;
        if (typeSchedule) {
            trains = trainDAO.findTrainByStationFrom(schBean.getStation());
        } else {
            trains = trainDAO.findTrainByStationTo(schBean.getStation());
        }

        log.debug("Send ScheduleRespondInfo to client");
        setScheduleTrainsByStation(trains);
        return;
    }

    public void findTrain() throws IOException {
        log.debug("Start method \"findTrain\"");
        List<Object[]> trains = trainDAO.findTrain(findTrBean.getStationFrom(),
                findTrBean.getStationTo(), findTrBean.getDateFrom(), findTrBean.getDateTo());

        log.debug("Send FindTrainRespondInfo to client");
        setScheduleTrainsByStation(trains);
        return;
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

    public List<Object[]> getScheduleTrainsByStation() {
        return scheduleTrainsByStation;
    }

    public void setScheduleTrainsByStation(List<Object[]> scheduleTrainsByStation) {
        this.scheduleTrainsByStation = scheduleTrainsByStation;
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

    public List<Object[]> getViewAllTrains() {
        return viewAllTrains;
    }

    public void setViewAllTrains(List<Object[]> viewAllTrains) {
        this.viewAllTrains = viewAllTrains;
    }
}
