package ru.tsystems.karpova.beans;

import ru.tsystems.karpova.service.TrainService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SessionScoped
@ManagedBean(name = "findTrainBean")
public class FindTrainBean {

    @EJB
    private TrainService trainService;

    private String stationFrom;
    private String stationTo;
    private Date dateFrom;
    private Date dateTo;
    private List<Object[]> scheduleTrainsByStation = new ArrayList<Object[]>();

    public void setStationFrom(String stationFrom) {
        this.stationFrom = stationFrom;
    }

    public void setStationTo(String stationTo) {
        this.stationTo = stationTo;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public String getStationFrom() {
        return stationFrom;
    }

    public String getStationTo() {
        return stationTo;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public List<Object[]> getScheduleTrainsByStation() {
        return scheduleTrainsByStation;
    }

    public void setScheduleTrainsByStation(List<Object[]> scheduleTrainsByStation) {
        this.scheduleTrainsByStation = scheduleTrainsByStation;
    }

    public void findTrain() throws IOException {
        scheduleTrainsByStation = trainService.findTrain(stationFrom, stationTo, dateFrom, dateTo);
    }
}
