package ru.tsystems.karpova.beans;

import ru.tsystems.karpova.service.StationService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@ManagedBean(name = "getAllStationsBean")
public class GetAllStationsBean implements Serializable {

    @EJB
    private StationService stationService;

    private List<String> allStations;

    public void setAllStations(List<String> allStations) {
        this.allStations = allStations;
    }

    public List<String> getAllStation() throws IOException {
        return allStations = stationService.getAllStation();
    }
}
