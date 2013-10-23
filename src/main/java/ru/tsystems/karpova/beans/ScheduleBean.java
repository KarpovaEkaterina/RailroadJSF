package ru.tsystems.karpova.beans;

import ru.tsystems.karpova.service.TrainService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@ManagedBean(name = "scheduleBean")
public class ScheduleBean implements Serializable {

    @EJB
    private TrainService trainService;

    private String station;
    private boolean typeSchedule = true;
    private List<Object[]> scheduleTrainsByStation = new ArrayList<Object[]>();

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public boolean isTypeSchedule() {
        return typeSchedule;
    }

    public void setTypeSchedule(boolean typeSchedule) {
        this.typeSchedule = typeSchedule;
    }

    public List<Object[]> getScheduleTrainsByStation() {
        return scheduleTrainsByStation;
    }

    public void setScheduleTrainsByStation(List<Object[]> scheduleTrainsByStation) {
        this.scheduleTrainsByStation = scheduleTrainsByStation;
    }

    public void scheduleByStation() throws IOException {
        scheduleTrainsByStation = trainService.scheduleByStation(station, typeSchedule);
    }
}
