package ru.tsystems.karpova.beans;

import ru.tsystems.karpova.service.TrainService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@ManagedBean(name = "viewAllTrainsBean")
public class ViewAllTrainsBean implements Serializable {

    @EJB
    private TrainService trainService;

    private List<Object[]> allTrains = new ArrayList<Object[]>();

    public List<Object[]> getAllTrains() throws IOException {
        return allTrains;
    }

    public void updateTrains() throws IOException {
        allTrains = trainService.viewAllTrains();
    }

    public void setAllTrains(List<Object[]> allTrains) {
        this.allTrains = allTrains;
    }
}
