package ru.tsystems.karpova.beans;

import java.util.Date;

public class AddWayBean {
    private String stationA;
    private String stationB;
    private Double price;
    private Date time;

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
}
