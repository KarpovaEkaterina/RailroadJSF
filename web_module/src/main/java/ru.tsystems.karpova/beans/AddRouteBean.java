package ru.tsystems.karpova.beans;

import ru.tsystems.karpova.service.RouteService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ViewScoped
@ManagedBean(name = "addRouteBean")
public class AddRouteBean implements Serializable {

    @EJB
    private RouteService routeService;
    @ManagedProperty(value = "#{addWayBean}")
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

    public String updatePage() {
        if ("Маршрут успешно добавлен".equals(message)) {
            addWaysForm = false;
            stationsForNewRoute = new ArrayList<String>();
            showDialog = "";
            routeName = "";
            addWayBean.setAddWaysResult("");
            return "manager_page.xhtml?faces-redirect=true";
        }
        return "";
    }

    public void addRoute() throws IOException {
        message = routeService.addRoute(stationsForNewRoute, routeName);
    }

    public void setAddWayBean(AddWayBean addWayBean) {
        this.addWayBean = addWayBean;
    }

    public void addWay() {
        if (addWayBean.getPrice() == null || addWayBean.getPrice() <= 0 || addWayBean.getTime() == null) {
            addWayBean.setAddWaysResult("Поля некорректно заполнены");
            return;
        }
        addWaysForm = false;
        stationsForNewRoute.add(newStation);
        addWayBean.setStationA(stationsForNewRoute.get(stationsForNewRoute.size() - 2));
        addWayBean.setStationB(stationsForNewRoute.get(stationsForNewRoute.size() - 1));
        addWayBean.setAddWaysResult(routeService.addWay(addWayBean.getPrice(), addWayBean.getTime(), addWayBean.getStationA(), addWayBean.getStationB()));
    }
}
