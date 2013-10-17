package ru.tsystems.karpova.respond;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class GetAllStationRespondInfo extends RespondInfo {

    private List<String> listAllStation;

    public List<String> getListAllStation() {
        return listAllStation;
    }

    public GetAllStationRespondInfo(List<String> listAllStation) {
        super(RespondInfo.OK_STATUS);
        this.listAllStation = listAllStation;
    }
}
