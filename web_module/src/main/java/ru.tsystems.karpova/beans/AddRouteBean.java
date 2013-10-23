package ru.tsystems.karpova.beans;

import java.io.IOException;
import java.lang.Object;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;

import ru.tsystems.karpova.service.RouteService;

@SessionScoped
@ManagedBean(name = "addRouteBean")
public class AddRouteBean {

    @EJB
    private RouteService routeService;
    private AddWayBean addWayBean;

    private String message = "";
    private String showDialog = "";
    private boolean addWaysForm = false;
    private String routeName;
    private List<String> stationsForNewRoute = new ArrayList<String>();
    private Map<String, Object[]> newWay;
    private String newStation;

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

    public boolean isAddWaysForm() {
        return addWaysForm;
    }

    public void setAddWaysForm(boolean addWaysForm) {
        this.addWaysForm = addWaysForm;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public List<String> getStationsForNewRoute() {
        return stationsForNewRoute;
    }

    public void setStationsForNewRoute(List<String> stationsForNewRoute) {
        this.stationsForNewRoute = stationsForNewRoute;
    }

    public Map<String, Object[]> getNewWay() {
        return newWay;
    }

    public void setNewWay(Map<String, Object[]> newWay) {
        this.newWay = newWay;
    }

    public String getNewStation() {
        return newStation;
    }

    public void setNewStation(String newStation) {
        this.newStation = newStation;
    }

    public boolean checkWay() {
        return routeService.checkWay(stationsForNewRoute, newStation);
    }

    public void addNewStationByList() {
        showDialog = "";
        addWaysForm = false;
        addWayBean.setAddWaysResult("");
        addWayBean.setPrice(0.0);
        addWayBean.setTime(null);
        if (!stationsForNewRoute.contains(newStation)) {
            showDialog = "Станция добавлена";
            if (stationsForNewRoute.size() > 0) {
                addWaysForm = checkWay();
            } else {
                stationsForNewRoute.add(newStation);
                addWaysForm = false;
            }
        } else {
            showDialog = "Станция уже была добавлена";
            addWaysForm = false;
        }
    }

    public void addRoute() {
        try {
            message = routeService.addRoute(stationsForNewRoute, routeName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAddWayBean(AddWayBean addWayBean) {
        this.addWayBean = addWayBean;
    }
}
