package ru.tsystems.karpova.beans;

import ru.tsystems.karpova.service.TicketService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@ManagedBean(name = "getAllTrainsBean")
public class GetAllTrainsBean implements Serializable {

    @EJB
    private TicketService ticketService;

    private List<String> allTrains;

    public void setAllTrains(List<String> allTrains) {
        this.allTrains = allTrains;
    }

    public List<String> getAllTrains() throws IOException {
        return allTrains = ticketService.getListAllTrains();
    }
}
