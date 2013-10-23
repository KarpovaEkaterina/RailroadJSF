package ru.tsystems.karpova.beans;

import ru.tsystems.karpova.service.RouteService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@ManagedBean(name = "getAllRoutesBean")
public class GetAllRoutesBean implements Serializable {

    @EJB
    private RouteService routeService;

    private List<String> allRoutes;

    public void setAllRoutes(List<String> allRoutes) {
        this.allRoutes = allRoutes;
    }

    public List<String> getAllRoutes() throws IOException {
        return allRoutes = routeService.getAllRoutes();
    }
}
