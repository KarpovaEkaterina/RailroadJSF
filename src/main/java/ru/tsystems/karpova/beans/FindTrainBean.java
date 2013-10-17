package ru.tsystems.karpova.beans;

import java.util.Date;

public class FindTrainBean {

    private String stationFrom;
    private String stationTo;
    private Date dateFrom;
    private Date dateTo;

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

    public FindTrainBean(String stationFrom, String stationTo, Date dateFrom, Date dateTo) {
        this.stationFrom = stationFrom;
        this.stationTo = stationTo;
        this.dateFrom = dateFrom;
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
}
