package main.java.ru.tsystems.karpova.beans;

import ru.tsystems.karpova.service.StationService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.IOException;

@SessionScoped
@ManagedBean(name = "addStationBean")
public class AddStationBean {

    @EJB
    private StationService stationService;

    private String stationName;
    private String message = "";

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationName() {
        return stationName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void addStation() throws IOException {
        message = stationService.addStation(stationName);
    }
}
