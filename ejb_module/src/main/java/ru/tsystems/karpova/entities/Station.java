package ru.tsystems.karpova.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Station {
    private int id;
    private String name;
    private Collection<Ticket> ticketsById;
    private Collection<Ticket> ticketsById_0;
    private Collection<Way> waysById;
    private Collection<Way> waysById_0;

    public Station(String stationName) {
        this.name = stationName.toLowerCase();
    }

    public Station() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Station station = (Station) o;

        if (id != station.id) return false;
        if (name != null ? !name.equals(station.name) : station.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "stationByStationFrom")
    public Collection<Ticket> getTicketsById() {
        return ticketsById;
    }

    public void setTicketsById(Collection<Ticket> ticketsById) {
        this.ticketsById = ticketsById;
    }

    @OneToMany(mappedBy = "stationByStationTo")
    public Collection<Ticket> getTicketsById_0() {
        return ticketsById_0;
    }

    public void setTicketsById_0(Collection<Ticket> ticketsById_0) {
        this.ticketsById_0 = ticketsById_0;
    }

    @OneToMany(mappedBy = "stationByIdStation1")
    public Collection<Way> getWaysById() {
        return waysById;
    }

    public void setWaysById(Collection<Way> waysById) {
        this.waysById = waysById;
    }

    @OneToMany(mappedBy = "stationByIdStation2")
    public Collection<Way> getWaysById_0() {
        return waysById_0;
    }

    public void setWaysById_0(Collection<Way> waysById_0) {
        this.waysById_0 = waysById_0;
    }
}
