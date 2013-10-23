package ru.tsystems.karpova.beans;

import ru.tsystems.karpova.service.RouteService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Date;

@SessionScoped
@ManagedBean(name = "addWayBean")
public class AddWayBean implements Serializable {

    @EJB
    private RouteService routeService;

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
}
