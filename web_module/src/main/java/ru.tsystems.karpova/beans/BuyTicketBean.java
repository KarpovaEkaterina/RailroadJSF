package main.java.ru.tsystems.karpova.beans;

import ru.tsystems.karpova.service.TicketService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SessionScoped
@ManagedBean(name = "buyTicketBean")
public class BuyTicketBean {

    @EJB
    private TicketService ticketService;

    private String train;
    private String stationFrom;
    private String stationTo;
    private String firstname;
    private String lastname;
    private Date birthday;
    private List<String> listAllStationsByTrain = new ArrayList<String>();
    private String message = "";

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

    public void updateStationsByTrain() {
        listAllStationsByTrain = ticketService.updateStationsByTrain(train);
    }

    public List<String> getListAllStationsByTrain() {
        return listAllStationsByTrain;
    }

    public void setListAllStationsByTrain(List<String> listAllStationsByTrain) {
        this.listAllStationsByTrain = listAllStationsByTrain;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void buyTicket() throws IOException {
       message = ticketService.buyTicket(train, stationFrom, stationTo, firstname, lastname, birthday);
    }
}
