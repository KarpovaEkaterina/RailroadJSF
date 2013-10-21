package ru.tsystems.karpova.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddRouteBean {

    private String routeName;
    private List<String> stationsForNewRoute = new ArrayList<String>();
    private Map<String, Object[]> newWay;
    private String newStation;

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public void setStationsForNewRoute(List<String> stationsForNewRoute) {
        this.stationsForNewRoute = stationsForNewRoute;
    }

    public void setNewWay(Map<String, Object[]> newWay) {
        this.newWay = newWay;
    }

    public String getRouteName() {
        return routeName;
    }

    public List<String> getStationsForNewRoute() {
        return stationsForNewRoute;
    }

    public Map<String, Object[]> getNewWay() {
        return newWay;
    }

    public String getNewStation() {
        return newStation;
    }

    public void setNewStation(String newStation) {
        this.newStation = newStation;
    }
}
