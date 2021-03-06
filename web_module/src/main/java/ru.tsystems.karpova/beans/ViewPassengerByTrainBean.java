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
@ManagedBean(name = "viewPassengerByTrainBean")
public class ViewPassengerByTrainBean implements Serializable {

    @EJB
    private TrainService trainService;

    private String trainName;
    private List<Object[]> allPassengerByTrain = new ArrayList<Object[]>();

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainName() {
        return trainName;
    }

    public List<Object[]> getAllPassengerByTrain() {
        return allPassengerByTrain;
    }

    public void setAllPassengerByTrain(List<Object[]> allPassengerByTrain) {
        this.allPassengerByTrain = allPassengerByTrain;
    }

    public void viewPassengerByTrain() throws IOException {
        allPassengerByTrain = trainService.viewPassengerByTrain(trainName);
    }
}
