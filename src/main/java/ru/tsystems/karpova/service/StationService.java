package ru.tsystems.karpova.service;

import org.apache.log4j.Logger;
import ru.tsystems.karpova.beans.AddStationBean;
import ru.tsystems.karpova.entities.User;
import ru.tsystems.karpova.respond.AddStationRespondInfo;
import ru.tsystems.karpova.respond.GetAllStationRespondInfo;
import ru.tsystems.karpova.dao.StationDAO;
import ru.tsystems.karpova.entities.Station;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StationService {

    private static Logger log = Logger.getLogger(StationService.class);

    private StationDAO stationDAO;
    private User user;
    private AddStationBean stationBean;
    private String message = "";

    public StationService() {
        this.stationDAO = new StationDAO();
    }

    public void addStation() throws IOException {
        log.debug("Start method \"addStation\"");
        if (stationBean.getStationName() == null || "".equals(stationBean.getStationName())) {
            message = "Введите название станции";
            return;
        }
        List<String> allStation = getAllStation();
        if (allStation.contains(stationBean.getStationName())) {
            log.debug("Send AddStationRespondInfo to client with STATION_ALREADY_EXISTS");
            message = "Станция с таким названием уже существует";
            return;
        }
        Station station = new Station(stationBean.getStationName());
        if (!stationDAO.saveStation(station)) {
            log.debug("Send AddStationRespondInfo to client with SERVER_ERROR_STATUS");
            message = "Server error status";
            return;
        } else {
            log.debug("Send AddStationRespondInfo to client with OK_STATUS");
            message = "Станция успешно добавлена";
            return;
        }
    }

    public List<String> getAllStation() throws IOException {
        log.debug("Start method \"getAllStation\"");
        List<Station> allStationList = stationDAO.getAllStation();
        List<String> allStation = new ArrayList<String>();
        for (Station station : allStationList) {
            allStation.add(station.getName());
        }
        log.debug("Send GetAllStationRespondInfo to client");
        return allStation;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setStationBean(AddStationBean stationBean) {
        this.stationBean = stationBean;
    }

    public AddStationBean getStationBean() {
        return stationBean;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
