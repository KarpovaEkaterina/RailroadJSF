package ru.tsystems.karpova.service;

import org.apache.log4j.Logger;
import ru.tsystems.karpova.dao.StationDAO;
import ru.tsystems.karpova.entities.Station;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class StationService {

    private static Logger log = Logger.getLogger(StationService.class);

    @EJB
    private StationDAO stationDAO;

    public String addStation(String stationName) throws IOException {
        String message = "";
        log.debug("Start method \"addStation\"");
        if (stationName == null || "".equals(stationName)) {
            message = "Введите название станции";
            return message;
        }
        List<String> allStation = getAllStation();
        if (allStation.contains(stationName)) {
            log.debug("Send AddStationRespondInfo to client with STATION_ALREADY_EXISTS");
            message = "Станция с таким названием уже существует";
            return message;
        }
        Station station = new Station(stationName);
        if (!stationDAO.saveStation(station)) {
            log.debug("Send AddStationRespondInfo to client with SERVER_ERROR_STATUS");
            message = "Server error status";
            return message;
        } else {
            log.debug("Send AddStationRespondInfo to client with OK_STATUS");
            message = "Станция успешно добавлена";
            return message;
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
}
