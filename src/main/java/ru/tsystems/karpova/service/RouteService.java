package ru.tsystems.karpova.service;

import org.apache.log4j.Logger;
import ru.tsystems.karpova.beans.AddRouteBean;
import ru.tsystems.karpova.beans.AddWayBean;
import ru.tsystems.karpova.entities.*;
import ru.tsystems.karpova.respond.AddRouteRespondInfo;
import ru.tsystems.karpova.respond.GetAllRoutesRespondInfo;
import ru.tsystems.karpova.dao.RouteDAO;
import ru.tsystems.karpova.dao.ScheduleDAO;
import ru.tsystems.karpova.dao.StationDAO;
import ru.tsystems.karpova.dao.WayDAO;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RouteService {

    private static Logger log = Logger.getLogger(RouteService.class);

    private RouteDAO routeDAO;
    private StationDAO stationDAO;
    private WayDAO wayDAO;
    private ScheduleDAO scheduleDAO;
    private User user;
    private AddRouteBean addNewRouteBean;
    private String message = "";
    private String showDialog = "";
    private boolean addWaysForm = false;
    private String addWaysResult = "";
    private AddWayBean addNewWayBean;

    public RouteService() {
        routeDAO = new RouteDAO();
        stationDAO = new StationDAO();
        wayDAO = new WayDAO();
        scheduleDAO = new ScheduleDAO();
    }

    public boolean checkWay() {
        List<Object[]> allWays = wayDAO.getAllWays();
        for (Object[] way : allWays) {
            if (way[0].equals(addNewRouteBean.getStationsForNewRoute().get(addNewRouteBean.getStationsForNewRoute().size() - 1))
                    && way[1].equals(addNewRouteBean.getNewStation())) {
                addNewRouteBean.getStationsForNewRoute().add(addNewRouteBean.getNewStation());
                return false;
            }
        }
        return true;
    }

    public void addWay() {
        if ("".equals(addNewWayBean.getPrice()) || addNewWayBean.getPrice() <= 0 || addNewWayBean.getTime() == null) {
            addWaysResult = "Поля некорректно заполнены";
            return;
        }
        Way way = new Way();
        addNewRouteBean.getStationsForNewRoute().add(addNewRouteBean.getNewStation());
        addNewWayBean.setStationA(addNewRouteBean.getStationsForNewRoute().get(addNewRouteBean.getStationsForNewRoute().size() - 2));
        addNewWayBean.setStationB(addNewRouteBean.getStationsForNewRoute().get(addNewRouteBean.getStationsForNewRoute().size() - 1));
        way.setStationByIdStation1(stationDAO.loadStationByName(addNewWayBean.getStationA()));
        way.setStationByIdStation2(stationDAO.loadStationByName(addNewWayBean.getStationB()));
        way.setPrice(addNewWayBean.getPrice());
        way.setTime(new Timestamp(addNewWayBean.getTime().getTime()));
        if (!wayDAO.saveWay(way)) {
            addWaysResult = "Server error";
            return;
        }
        addWaysResult = "Путь добавлен";
    }

    public void addNewStationByList() {
        showDialog = "";
        addWaysForm = false;
        addWaysResult = "";
        addNewWayBean.setPrice(0.0);
        addNewWayBean.setTime(null);
        if (!addNewRouteBean.getStationsForNewRoute().contains(addNewRouteBean.getNewStation())) {
            showDialog = "Станция добавлена";
            if (addNewRouteBean.getStationsForNewRoute().size() > 0) {
                addWaysForm = checkWay();
            } else {
                addNewRouteBean.getStationsForNewRoute().add(addNewRouteBean.getNewStation());
                addWaysForm = false;
            }
        } else {
            showDialog = "Станция уже была добавлена";
            addWaysForm = false;
        }
    }

    public void addRoute() throws IOException {
        log.debug("Start method \"addRoute\"");
        if(addNewRouteBean.getStationsForNewRoute().size() <= 1) {
            message = "Маршрут должен содержать больше одной станции";
            return;
        }
        if ("".equals(addNewRouteBean.getRouteName())){
            message = "Введите название ";
            return;
        }
        Route route = new Route();
        route.setName(addNewRouteBean.getRouteName());
        if (!routeDAO.saveRoute(route)) {
            log.debug("Send AddRouteRespondInfo to client with SERVER_ERROR_STATUS");
            message = "Server error status";
            return;
        }
        List<Schedule> schedules = new ArrayList<Schedule>();
        route = routeDAO.loadRoute(route.getName());
        for (int i = 1; i < addNewRouteBean.getStationsForNewRoute().size(); i++) {
            Way way = wayDAO.loadWayByStations(addNewRouteBean.getStationsForNewRoute().get(i - 1), addNewRouteBean.getStationsForNewRoute().get(i));
            Schedule schedule = new Schedule();
            schedule.setRouteByIdRoute(route);
            schedule.setWayByIdWay(way);
            schedule.setSeqNumber(i);
            schedules.add(schedule);
            if (!scheduleDAO.saveSchedule(schedule)) {
                log.debug("Send AddRouteRespondInfo to client with SERVER_ERROR_STATUS");
                message = "Server error status";
                return;
            }
        }
        log.debug("Send AddRouteRespondInfo to client with OK_STATUS");
        message = "Маршрут успешно добавлен";
        return;
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

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setAddNewRouteBean(AddRouteBean addNewRouteBean) {
        this.addNewRouteBean = addNewRouteBean;
    }

    public AddRouteBean getAddNewRouteBean() {
        return addNewRouteBean;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getShowDialog() {
        return showDialog;
    }

    public void setShowDialog(String showDialog) {
        this.showDialog = showDialog;
    }

    public boolean getAddWaysForm() {
        return addWaysForm;
    }

    public void setAddWaysForm(boolean addWaysForm) {
        this.addWaysForm = addWaysForm;
    }

    public void setAddNewWayBean(AddWayBean addNewWayBean) {
        this.addNewWayBean = addNewWayBean;
    }

    public AddWayBean getAddNewWayBean() {
        return addNewWayBean;
    }

    public String getAddWaysResult() {
        return addWaysResult;
    }

    public void setAddWaysResult(String addWaysResult) {
        this.addWaysResult = addWaysResult;
    }
}
