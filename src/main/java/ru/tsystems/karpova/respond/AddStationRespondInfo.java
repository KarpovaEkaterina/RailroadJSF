package ru.tsystems.karpova.respond;

import java.io.Serializable;

public class AddStationRespondInfo extends RespondInfo{
    private int status;

    public AddStationRespondInfo(int status) {
        super(status);
    }
}
