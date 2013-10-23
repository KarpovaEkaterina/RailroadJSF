package ru.tsystems.karpova.beans;

import ru.tsystems.karpova.service.TicketService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.util.List;

@SessionScoped
@ManagedBean(name = "getAllTrainsBean")
public class GetAllTrainsBean {

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
