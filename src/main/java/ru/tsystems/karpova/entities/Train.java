package ru.tsystems.karpova.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
public class Train {
    private int id;
    private String name;
    private int totalSeats;
    private Timestamp departure;
    private Collection<Ticket> ticketsById;
    private Route routeByIdRoute;

    public Train(String name, int totalSeats, Timestamp departure, Route routeByIdRoute) {
        this.name = name.toLowerCase();
        this.totalSeats = totalSeats;
        this.departure = departure;
        this.routeByIdRoute = routeByIdRoute;
    }

    public Train() {
    }

    @Column(name = "id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name")
    @Basic
    public String getName() {
        return name.toLowerCase();
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "total_seats")
    @Basic
    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    @Column(name = "departure")
    @Basic
    public Timestamp getDeparture() {
        return departure;
    }

    public void setDeparture(Timestamp departure) {
        this.departure = departure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Train train = (Train) o;

        if (id != train.id) return false;
        if (totalSeats != train.totalSeats) return false;
        if (departure != null ? !departure.equals(train.departure) : train.departure != null) return false;
        if (name != null ? !name.equals(train.name) : train.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + totalSeats;
        result = 31 * result + (departure != null ? departure.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "trainByIdTrain")
    public Collection<Ticket> getTicketsById() {
        return ticketsById;
    }

    public void setTicketsById(Collection<Ticket> ticketsById) {
        this.ticketsById = ticketsById;
    }

    @ManyToOne
    @JoinColumn(name = "id_route", referencedColumnName = "id", nullable = false)
    public Route getRouteByIdRoute() {
        return routeByIdRoute;
    }

    public void setRouteByIdRoute(Route routeByIdRoute) {
        this.routeByIdRoute = routeByIdRoute;
    }
}
