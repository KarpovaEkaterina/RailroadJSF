package ru.tsystems.karpova.beans;

import ru.tsystems.karpova.service.TrainService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SessionScoped
@ManagedBean(name = "viewAllTrainsBean")
public class ViewAllTrainsBean {

    @EJB
    private TrainService trainService;

    private List<Object[]> allTrains = new ArrayList<Object[]>();

    public List<Object[]> getAllTrains() throws IOException {
        allTrains = trainService.viewAllTrains();
        return allTrains;
    }

    public void setAllTrains(List<Object[]> allTrains) {
        this.allTrains = allTrains;
    }
}
