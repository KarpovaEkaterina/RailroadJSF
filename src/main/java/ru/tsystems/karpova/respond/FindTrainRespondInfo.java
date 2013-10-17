package ru.tsystems.karpova.respond;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FindTrainRespondInfo extends RespondInfo {

    private List<TrainInfo> trains = new ArrayList<TrainInfo>();

    public FindTrainRespondInfo(List<Object[]> trains) {
        super(OK_STATUS);
        for (Object[] train : trains) {
            TrainInfo trainInfo = new TrainInfo((String) train[0], (Date) train[1]);
            this.trains.add(trainInfo);
        }
    }

    public List<TrainInfo> getTrains() {
        return trains;
    }

    public static class TrainInfo {

        private String trainName;
        private Date departure;

        public TrainInfo(String station, Date departure) {
            this.trainName = station;
            this.departure = departure;
        }

        public Date getDeparture() {
            return departure;
        }

        public String getTrainName() {

            return trainName;
        }
    }
}
