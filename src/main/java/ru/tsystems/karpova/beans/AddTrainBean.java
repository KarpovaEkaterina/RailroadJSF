package ru.tsystems.karpova.beans;

import java.util.Date;

public class AddTrainBean {

    private String trainName;
    private String route;
    private int totalSeats;
    private Date departureTime;

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public void setTotalSeats(String totalSeats) {
        this.totalSeats = Integer.parseInt(totalSeats);
    }

    public String getTrainName() {
        return trainName;
    }

    public String getRoute() {
        return route;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public Date getDepartureTime() {
        return departureTime;
    }
}
