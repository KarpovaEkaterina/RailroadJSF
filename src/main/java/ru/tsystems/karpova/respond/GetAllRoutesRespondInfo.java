package ru.tsystems.karpova.respond;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class GetAllRoutesRespondInfo extends RespondInfo {
    private List<String> listAllRoutes;

    public List<String> getListAllRoutes() {
        return listAllRoutes;
    }

    public GetAllRoutesRespondInfo(List<String> listAllRoutes) {
        super(RespondInfo.OK_STATUS);
        this.listAllRoutes = listAllRoutes;
    }
}
