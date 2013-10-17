package ru.tsystems.karpova.beans;

import java.util.Date;

public class BuyTicketBean {

    private String train;
    private String stationFrom;
    private String stationTo;
    private String firstname;
    private String lastname;
    private Date birthday;

    public void setTrain(String train) {
        this.train = train;
    }

    public void setStationFrom(String stationFrom) {
        this.stationFrom = stationFrom;
    }

    public void setStationTo(String stationTo) {
        this.stationTo = stationTo;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getStationFrom() {
        return stationFrom;
    }

    public String getStationTo() {
        return stationTo;
    }

    public String getTrain() {
        return train;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Date getBirthday() {
        return birthday;
    }
}
