package ru.tsystems.karpova.beans;

import ru.tsystems.karpova.service.TrainService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ViewScoped
@ManagedBean(name = "addTrainBean")
public class AddTrainBean implements Serializable {


    @EJB
    private TrainService trainService;

    private String trainName;
    private String route;
    private int totalSeats;
    private Date departureTime;
    private String message = "";
    private List<Integer> totalSeatsValues;

    public int getTotalSeats() {
        return totalSeats;
    }

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

    public List<Integer> getTotalSeatsValues() {
        totalSeatsValues = new ArrayList<Integer>();
        for (int i = 1; i <= 100; i++) {
            totalSeatsValues.add(i);
        }
        return totalSeatsValues;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void addTrain() throws IOException {
        message = trainService.addTrain(trainName, route, totalSeats, departureTime);
    }

    public String updatePage() {
        if ("Поезд добавлен".equals(message)) {
            departureTime = null;
            totalSeats = 0;
            trainName = "";
            route = "";
            return "manager_page.xhtml?faces-redirect=true";
        }
        return "";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTotalSeatsValues(List<Integer> totalSeatsValues) {
        this.totalSeatsValues = totalSeatsValues;
    }
}
