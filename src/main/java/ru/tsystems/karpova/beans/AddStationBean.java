package ru.tsystems.karpova.beans;

public class AddStationBean {

    private String stationName;

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public AddStationBean(String stationName) {
        this.stationName = stationName;
    }

    public String getStationName() {
        return stationName;
    }
}
