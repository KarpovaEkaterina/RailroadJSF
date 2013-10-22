package main.java.ru.tsystems.karpova.beans;

import ru.tsystems.karpova.service.TrainService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SessionScoped
@ManagedBean(name = "addTrainBean")
public class AddTrainBean {


    @EJB
    private TrainService trainService;

    private String trainName;
    private String route;
    private int totalSeats;
    private Date departureTime;
    private String message = "";

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

    public List<Integer> getTotalSeats() {
        List<Integer> totalSeats = new ArrayList<Integer>();
        for (int i = 1; i <= 100; i++) {
            totalSeats.add(i);
        }
        return totalSeats;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void addStation() throws IOException {
        message = trainService.addTrain(trainName, route, totalSeats, departureTime);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
