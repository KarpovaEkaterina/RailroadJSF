package main.java.ru.tsystems.karpova.beans;

import ru.tsystems.karpova.service.StationService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.util.List;

@SessionScoped
@ManagedBean(name = "getAllStationsBean")
public class GetAllStationsBean {

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
