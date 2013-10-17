package ru.tsystems.karpova.beans;

import java.util.List;
import java.util.Map;

public class AddRouteBean {

    private String routeName;
    private List<String> stationsForNewRoute;
    private Map<String, Object[]> newWay;
    private String delimiter;

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public void setStationsForNewRoute(List<String> stationsForNewRoute) {
        this.stationsForNewRoute = stationsForNewRoute;
    }

    public void setNewWay(Map<String, Object[]> newWay) {
        this.newWay = newWay;
    }

    public String getDelimiter() {
        return delimiter;
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

}
