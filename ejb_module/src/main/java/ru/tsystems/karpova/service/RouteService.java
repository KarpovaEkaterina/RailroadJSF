package ru.tsystems.karpova.service;

import org.apache.log4j.Logger;
import ru.tsystems.karpova.dao.RouteDAO;
import ru.tsystems.karpova.dao.ScheduleDAO;
import ru.tsystems.karpova.dao.StationDAO;
import ru.tsystems.karpova.dao.WayDAO;
import ru.tsystems.karpova.entities.Route;
import ru.tsystems.karpova.entities.Schedule;
import ru.tsystems.karpova.entities.Way;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
public class RouteService {

    private static final Logger log = Logger.getLogger(RouteService.class);

    @EJB
    private RouteDAO routeDAO;
    @EJB
    private StationDAO stationDAO;
    @EJB
    private WayDAO wayDAO;
    @EJB
    private ScheduleDAO scheduleDAO;

    public boolean checkWay(List<String> stationsForNewRoute, String newStation) {
        List<Object[]> allWays = wayDAO.getAllWays();
        for (Object[] way : allWays) {
            if (way[0].equals(stationsForNewRoute.get(stationsForNewRoute.size() - 1))
                    && way[1].equals(newStation)) {
                stationsForNewRoute.add(newStation);
                return false;
            }
        }
        return true;
    }

    public String addWay(Double price, Date time, String stationA, String stationB) {
        String addWaysResult = "";
        Way way = new Way();
        way.setStationByIdStation1(stationDAO.loadStationByName(stationA));
        way.setStationByIdStation2(stationDAO.loadStationByName(stationB));
        way.setPrice(price);
        way.setTime(new Timestamp(time.getTime()));
        if (!wayDAO.saveWay(way)) {
            addWaysResult = "Server error";
            return addWaysResult;
        }
        addWaysResult = "Путь добавлен";
        return addWaysResult;
    }

    public String addRoute(List<String> stationsForNewRoute, String routeName) throws IOException {
        String message = "";
        log.debug("Start method \"addRoute\"");
        if (stationsForNewRoute.size() <= 1) {
            message = "Маршрут должен содержать больше одной станции";
            return message;
        }
        if ("".equals(routeName)) {
            message = "Введите название ";
            return message;
        }
        Route route = new Route();
        route.setName(routeName);
        if (!routeDAO.saveRoute(route)) {
            log.debug("Send AddRouteRespondInfo to client with SERVER_ERROR_STATUS");
            message = "Server error status";
            return message;
        }
        List<Schedule> schedules = new ArrayList<Schedule>();
        route = routeDAO.loadRoute(route.getName());
        for (int i = 1; i < stationsForNewRoute.size(); i++) {
            Way way = wayDAO.loadWayByStations(stationsForNewRoute.get(i - 1), stationsForNewRoute.get(i));
            Schedule schedule = new Schedule();
            schedule.setRouteByIdRoute(route);
            schedule.setWayByIdWay(way);
            schedule.setSeqNumber(i);
            schedules.add(schedule);
            if (!scheduleDAO.saveSchedule(schedule)) {
                log.debug("Send AddRouteRespondInfo to client with SERVER_ERROR_STATUS");
                message = "Server error status";
                return message;
            }
        }
        log.debug("Send AddRouteRespondInfo to client with OK_STATUS");
        message = "Маршрут успешно добавлен";
        return message;
    }

    public List<String> getAllRoutes() throws IOException {
        log.debug("Start method \"getAllRoutes\"");
        List<Route> allRoutesList = routeDAO.getAllRoutes();
        List<String> allRoutes = new ArrayList<String>();
        for (Route route : allRoutesList) {
            allRoutes.add(route.getName());
        }
        log.debug("Send GetAllRoutesRespondInfo to client");
        return allRoutes;
    }
}
