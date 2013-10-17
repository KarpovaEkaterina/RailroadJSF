package ru.tsystems.karpova.respond;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class AddTrainRespondInfo extends RespondInfo {
    public static final int WRONG_ROUTE_NAME_STATUS = 2;

    private int status;

    public AddTrainRespondInfo(int status) {
        super(status);
    }
}
