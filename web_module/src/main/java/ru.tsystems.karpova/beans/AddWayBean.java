package ru.tsystems.karpova.beans;

import ru.tsystems.karpova.service.RouteService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Date;

@SessionScoped
@ManagedBean(name = "addWayBean")
public class AddWayBean {

    @EJB
    private RouteService routeService;
    private AddRouteBean addRouteBean;

    private String stationA;
    private String stationB;
    private Double price;
    private Date time;
    private String addWaysResult = "";

    public String getStationA() {
        return stationA;
    }

    public void setStationA(String stationA) {
        this.stationA = stationA;
    }

    public String getStationB() {
        return stationB;
    }

    public void setStationB(String stationB) {
        this.stationB = stationB;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getAddWaysResult() {
        return addWaysResult;
    }

    public void setAddWaysResult(String addWaysResult) {
        this.addWaysResult = addWaysResult;
    }

    public void addWay(){
        if ("".equals(price) || price <= 0 || time == null) {
            addWaysResult = "Поля некорректно заполнены";
            return;
        }
        addRouteBean.getStationsForNewRoute().add(addRouteBean.getNewStation());
        setStationA(addRouteBean.getStationsForNewRoute().get(addRouteBean.getStationsForNewRoute().size() - 2));
        setStationB(addRouteBean.getStationsForNewRoute().get(addRouteBean.getStationsForNewRoute().size() - 1));
        addWaysResult = routeService.addWay(price, time, stationA, stationB);
    }

    public void setAddRouteBean(AddRouteBean addRouteBean) {
        this.addRouteBean = addRouteBean;
    }
}
