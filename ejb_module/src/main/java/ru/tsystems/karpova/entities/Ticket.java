package ru.tsystems.karpova.entities;

import javax.persistence.*;

@Entity
public class Ticket {
    private int id;
    private double price;
    private Passenger passengerByIdPassenger;
    private Station stationByStationFrom;
    private Station stationByStationTo;
    private Train trainByIdTrain;

    @Column(name = "id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "price")
    @Basic
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ticket ticket = (Ticket) o;

        if (id != ticket.id) return false;
        if (Double.compare(ticket.price, price) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_passenger", referencedColumnName = "id", nullable = false)
    public Passenger getPassengerByIdPassenger() {
        return passengerByIdPassenger;
    }

    public void setPassengerByIdPassenger(Passenger passengerByIdPassenger) {
        this.passengerByIdPassenger = passengerByIdPassenger;
    }

    @ManyToOne
    @JoinColumn(name = "station_from", referencedColumnName = "id", nullable = false)
    public Station getStationByStationFrom() {
        return stationByStationFrom;
    }

    public void setStationByStationFrom(Station stationByStationFrom) {
        this.stationByStationFrom = stationByStationFrom;
    }

    @ManyToOne
    @JoinColumn(name = "station_to", referencedColumnName = "id", nullable = false)
    public Station getStationByStationTo() {
        return stationByStationTo;
    }

    public void setStationByStationTo(Station stationByStationTo) {
        this.stationByStationTo = stationByStationTo;
    }

    @ManyToOne
    @JoinColumn(name = "id_train", referencedColumnName = "id", nullable = false)
    public Train getTrainByIdTrain() {
        return trainByIdTrain;
    }

    public void setTrainByIdTrain(Train trainByIdTrain) {
        this.trainByIdTrain = trainByIdTrain;
    }
}
