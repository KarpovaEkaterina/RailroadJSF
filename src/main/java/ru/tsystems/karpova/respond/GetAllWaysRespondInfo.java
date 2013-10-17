package ru.tsystems.karpova.respond;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

public class GetAllWaysRespondInfo extends RespondInfo {

    private String delimiter;
    private Map<String, Object[]> listAllWays;

    public GetAllWaysRespondInfo(Map<String, Object[]> listAllWays, String delimiter) {
        super(RespondInfo.OK_STATUS);
        this.listAllWays = listAllWays;
        this.delimiter = delimiter;
    }

    public GetAllWaysRespondInfo(int errorStatus) {
        super(errorStatus);
    }

    public Map<String, Object[]> getListAllWays() {
        return listAllWays;
    }

    public String getDelimiter() {
        return delimiter;
    }
}
