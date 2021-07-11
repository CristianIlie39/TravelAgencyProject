package persistence.entities;

import business.dto.CityDTO;
import business.dto.FlightDTO;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@NamedQueries({
        @NamedQuery(name = "findAirportByName", query = "SELECT airport FROM Airport airport WHERE " +
                "airport.name = :name"),
        @NamedQuery(name = "countAirport", query = "SELECT COUNT(*) FROM Airport airport WHERE " +
                "airport.name = :airportName AND airport.city.name = :cityName"),
        @NamedQuery(name = "findAirportsByCityName", query = "SELECT airport FROM Airport airport WHERE " +
                "airport.city.name = :cityName"),
        @NamedQuery(name = "updateAirportName", query = "UPDATE Airport airport SET airport.name = :newName WHERE " +
                "airport.name = :currentName"),
        @NamedQuery(name = "deleteAirportByName", query = "DELETE Airport airport WHERE " +
                "airport.name = :airportName")
})

@Entity
@Table(name = "airports")
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "cities_id")
    private City city;

    @OneToMany(mappedBy = "airport", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Flight> flightSet;

    @OneToMany(mappedBy = "airport", fetch = FetchType.LAZY)
    private Set<Trip> tripSet;

    public Airport(String name, City city, Set<Flight> flightSet) {
        this.name = name;
        this.city = city;
        this.flightSet = flightSet;
    }

    public Airport() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Set<Flight> getFlightSet() {
        return flightSet;
    }

    public void setFlightSet(Set<Flight> flightSet) {
        this.flightSet = flightSet;
    }

    public Set<Trip> getTripSet() {
        return tripSet;
    }

    public void setTripSet(Set<Trip> tripSet) {
        this.tripSet = tripSet;
    }

    public String toString() {
        return "Airport: " + name + " , " + city.getName();
    }

    public boolean equals(Object object) {
        if (object instanceof Airport) {
            if (this.id == ((Airport) object).getId() && this.name.equals(((Airport) object).getName()) &&
                    this.city.equals(((Airport) object).getCity()) &&
                    this.flightSet.equals(((Airport) object).getFlightSet())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(id, name, city, flightSet);
    }

}
