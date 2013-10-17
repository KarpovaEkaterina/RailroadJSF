package ru.tsystems.karpova.respond;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class GetAllTrainsRespondInfo extends RespondInfo {

    private List<Object[]> allTrains;

    public GetAllTrainsRespondInfo(List<Object[]> allTrains) {
        super(RespondInfo.OK_STATUS);
        this.allTrains = allTrains;
    }

    public List<Object[]> getListAllTrains() {
        return allTrains;
    }
}
