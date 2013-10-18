package ru.tsystems.karpova.service;

import org.apache.log4j.Logger;
import ru.tsystems.karpova.beans.BuyTicketBean;
import ru.tsystems.karpova.entities.*;
import ru.tsystems.karpova.dao.PassengerDAO;
import ru.tsystems.karpova.dao.StationDAO;
import ru.tsystems.karpova.dao.TicketDAO;
import ru.tsystems.karpova.dao.TrainDAO;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TicketService {

    private static Logger log = Logger.getLogger(TicketService.class);

    private TicketDAO ticketDAO;
    private PassengerDAO passengerDAO;
    private TrainDAO trainDAO;
    private StationDAO stationDAO;
    private String message = "";
    private BuyTicketBean buyTicBean;
    private User user;
    private List<String> listAllTrains = new ArrayList<String>();
    private List<String> listAllStationsByTrain = new ArrayList<String>();

    public TicketService() {
        ticketDAO = new TicketDAO();
        passengerDAO = new PassengerDAO();
        trainDAO = new TrainDAO();
        stationDAO = new StationDAO();
    }

    public String buyTicket() throws IOException {
        log.debug("Start method \"saveTicket\"");
        if ("".equals(buyTicBean.getTrain()) || "".equals(buyTicBean.getStationFrom()) || "".equals(buyTicBean.getStationTo())
                || buyTicBean.getBirthday() == null || "".equals(buyTicBean.getFirstname()) || "".equals(buyTicBean.getLastname())) {
            log.debug("Send BuyTicketRespondInfo to client with SERVER_ERROR_STATUS");
            message = "Все поля должны быть заполнены!";
            return message;
        }
        Train train = trainDAO.loadTrain(buyTicBean.getTrain());
        if (train == null) {
            log.debug("Send BuyTicketRespondInfo to client with WRONG_TRAIN_NAME_STATUS");
            message = "Server error";
            return message;
        }
        Station stationFrom = stationDAO.loadStationByName(buyTicBean.getStationFrom());
        if (stationFrom == null) {
            log.debug("Send BuyTicketRespondInfo to client with WRONG_STATION_FROM_NAME_STATUS");
            message = "Server error";
            return message;
        }
        Station stationTo = stationDAO.loadStationByName(buyTicBean.getStationTo());
        if (stationTo == null) {
            log.debug("Send BuyTicketRespondInfo to client with WRONG_STATION_TO_NAME_STATUS");
            message = "Server error";
            return message;
        }
        List allStationsByTrain = trainDAO.getAllStationsByTrain(train);
        if (!stationInList(stationFrom, allStationsByTrain) || !stationInList(stationTo, allStationsByTrain)) {
            log.debug("Send BuyTicketRespondInfo to client with WRONG_STATION_TRAIN_STATUS");
            message = "Поезд не проходит через эти станции";
            return message;
        }
        if (!stationFromBeforeStationToInList(stationFrom, stationTo, allStationsByTrain)) {
            log.debug("Send BuyTicketRespondInfo to client with WRONG_STATION_ORDER_STATUS");
            message = "Поезд идет в другом направлении";
            return message;
        }
        if (!trainDAO.checkDepartureTime(train, stationFrom)) {
            log.debug("Send GetAllRoutesRespondInfo to client with WRONG_DEPARTURE_TIME_STATUS");
            message = "Продажа билетов уже завершена";
            return message;
        }
        Passenger passenger = passengerDAO.loadPassenger(buyTicBean.getFirstname(),
                buyTicBean.getLastname(), new Timestamp(buyTicBean.getBirthday().getTime()));
        if (passenger == null) {
            passenger = new Passenger(buyTicBean.getFirstname(),
                    buyTicBean.getLastname(), new Timestamp(buyTicBean.getBirthday().getTime()));
            if (!passengerDAO.savePassenger(passenger)) {
                log.debug("Send BuyTicketRespondInfo to client with SERVER_ERROR_STATUS");
                message = "Server error";
                return message;
            }
            passenger = passengerDAO.loadPassenger(buyTicBean.getFirstname(),
                    buyTicBean.getLastname(), new Timestamp(buyTicBean.getBirthday().getTime()));
        }
        synchronized (TrainDAO.class) {
            HashMap<Integer, Integer[]> passengerByStation = trainDAO.countOfPassengerOnEveryStation(train);

            if (0 == calcFreeSeats(passengerByStation, allStationsByTrain, stationFrom, stationTo, train)) {
                log.debug("Send BuyTicketRespondInfo to client with NO_SEATS_STATUS");
                message = "Нет мест";
                return message;
            } else {
                if (trainDAO.isAlreadyExistPassengerOnTrain(train, passenger)) {
                    log.debug("Send BuyTicketRespondInfo to client with PASSENGER_ALREADY_EXISTS_STATUS");
                    message = "Пассажир уже зарегистрирован на поезд";
                    return message;
                } else {
                    Ticket ticket = new Ticket();
                    ticket.setTrainByIdTrain(train);
                    ticket.setPassengerByIdPassenger(passenger);
                    ticket.setStationByStationFrom(stationFrom);
                    ticket.setStationByStationTo(stationTo);
                    ticket.setPrice(100);
                    if (ticketDAO.saveTicket(ticket)) {
                        log.debug("Send BuyTicketRespondInfo to client with OK_STATUS");
                        message = "Билет куплен";
                        return message;
                    } else {
                        log.debug("Send BuyTicketRespondInfo to client with SERVER_ERROR_STATUS");
                        message = "Server error";
                        return message;
                    }
                }
            }
        }
    }

    public boolean stationFromBeforeStationToInList(Station stationFrom, Station stationTo, List<Object[]> allStationsByTrain) {
        log.debug("Start method \"stationFromBeforeStationToInList\"");
        boolean firstFound = false;
        for (Object[] obj : allStationsByTrain) {
            String stationName = (String) obj[1];
            if (stationFrom.getName().equals(stationName)) {
                firstFound = true;
            }
            if (stationTo.getName().equals(stationName)) {
                return firstFound;
            }
        }
        return false;
    }

    public boolean stationInList(Station station, List<Object[]> allStationsByTrain) {
        log.debug("Start method \"stationInList\"");
        for (Object[] obj : allStationsByTrain) {
            String stationName = (String) obj[1];
            if (station.getName().equals(stationName)) {
                return true;
            }
        }
        return false;
    }

    public int calcFreeSeats(HashMap<Integer, Integer[]> passengerByStation, List<Object[]> allStationsByTrain, Station stationFrom, Station stationTo, Train train) {
        log.debug("Start method \"calcFreeSeats\"");
        int occupiedSeats = 0;
        int maxOccupied = -1;
        boolean calcMax = false;
        for (Object[] obj : allStationsByTrain) {
            Integer stationId = (Integer) obj[0];
            String stationName = (String) obj[1];
            if (stationTo.getName().equals(stationName)) {
                break;
            }
            if (passengerByStation.containsKey(stationId)) {
                Integer[] change = passengerByStation.get(stationId);
                occupiedSeats += change[0] - change[1];
            }
            if (stationFrom.getName().equals(stationName)) {
                calcMax = true;
            }
            if (calcMax && maxOccupied < occupiedSeats) {
                maxOccupied = occupiedSeats;
            }
        }
        return train.getTotalSeats() - maxOccupied;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setBuyTicBean(BuyTicketBean buyTicBean) {
        this.buyTicBean = buyTicBean;
    }

    public BuyTicketBean getBuyTicBean() {
        return buyTicBean;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public List<String> getListAllTrains() {
        listAllTrains.clear();
        List<Train> listTrains = trainDAO.getAllTrains();
        for (Train train : listTrains) {
            listAllTrains.add(train.getName());
        }
        return listAllTrains;
    }

    public void setListAllTrains(List<String> listAllTrains) {
        this.listAllTrains = listAllTrains;
    }

    public List<String> getListAllStationsByTrain() {
        return listAllStationsByTrain;
    }

    public void updateStationsByTrain() {
        listAllStationsByTrain.clear();
        if (buyTicBean.getTrain() != null && !buyTicBean.getTrain().equals("")) {
            List<Object[]> allStationsByTrainName = trainDAO.getAllStationsByTrainName(buyTicBean.getTrain());
            for (Object[] station : allStationsByTrainName) {
                listAllStationsByTrain.add((String) station[1]);
            }
        } else {
            listAllStationsByTrain = new ArrayList<String>();
        }
    }

    public void setListAllStationsByTrain(List<String> listAllStationsByTrain) {
        this.listAllStationsByTrain = listAllStationsByTrain;
    }
}
