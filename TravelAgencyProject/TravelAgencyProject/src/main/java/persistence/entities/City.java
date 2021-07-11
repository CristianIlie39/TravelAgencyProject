package persistence.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@NamedQueries({
        @NamedQuery(name = "findCity", query = "SELECT city FROM City city WHERE city.name = :name"),
        @NamedQuery(name = "countCityByName", query = "SELECT COUNT(*) FROM City city WHERE city.name = :name"),
        @NamedQuery(name = "deleteCityByName", query = "DELETE FROM City city WHERE city.name = :name"),
        @NamedQuery(name = "findCitiesByCountry", query = "SELECT city FROM City city WHERE city.country.name = :name"),
        @NamedQuery(name = "findAllCities", query = "SELECT city FROM City city"),
        @NamedQuery(name = "updateCityName", query = "UPDATE City city SET city.name = :newName WHERE city.name = :currentName")
})

@Entity
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "countries_id")
    private Country country;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private Set<Airport> airportSet;

    @OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
    private Set<Hotel> hotelSet;

    public City(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    public City() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Set<Airport> getAirportSet() {
        return airportSet;
    }

    public void setAirportSet(Set<Airport> airportSet) {
        this.airportSet = airportSet;
    }

    public Set<Hotel> getHotelSet() {
        return hotelSet;
    }

    public void setHotelSet(Set<Hotel> hotelSet) {
        this.hotelSet = hotelSet;
    }

    public String toString() {
        return "City: " + name + " , " + country.getName();
    }

    public boolean equals(Object object) {
        if (object instanceof City) {
            if (this.id == ((City) object).getId() && this.name.equals(((City) object).getName()) && this.country.equals(((City) object).getCountry())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(id) + Objects.hash(name) + Objects.hash(country);
    }
}

