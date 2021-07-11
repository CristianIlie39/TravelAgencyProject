package persistence.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@NamedQueries({
        @NamedQuery(name = "findAllHotelsByCity", query = "SELECT hotel FROM Hotel hotel WHERE hotel.city.name =:name"),
        @NamedQuery(name = "findHotelByNameAndByCityName", query = "SELECT hotel FROM Hotel hotel WHERE " +
                "hotel.name = :hotelName AND hotel.city.name = :cityName"),
        @NamedQuery(name = "countHotelsByNameAndByCityName", query = "SELECT COUNT(*) FROM Hotel hotel WHERE" +
                " hotel.name = :hotelName AND hotel.city.name = :cityName"),
        @NamedQuery(name = "findHotelsByStarsInCity", query = "SELECT hotel FROM Hotel hotel WHERE " +
                "hotel.stars = :stars AND hotel.city.name = :cityName AND hotel.city.country.name = :countryName AND " +
                "hotel.city.country.continent.name = :continentName"),
        @NamedQuery(name = "updateHotelName", query = "UPDATE Hotel hotel SET hotel.name = :newName WHERE" +
                " hotel.name = :currentName"),
        @NamedQuery(name = "deleteHotelByName", query = "DELETE FROM Hotel hotel WHERE hotel.name = :name")
})

@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "stars")
    private int stars;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "cities_id")
    private City city;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Room> roomSet;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY)
    private Set<Trip> tripSet;

    public Hotel(String name, int stars, String description, City city, Set<Room> roomSet) {
        this.name = name;
        this.stars = stars;
        this.description = description;
        this.city = city;
        this.roomSet = roomSet;
    }

    public Hotel() {

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

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Set<Room> getRoomSet() {
        return roomSet;
    }

    public void setRoomSet(Set<Room> roomSet) {
        this.roomSet = roomSet;
    }

    public Set<Trip> getTripSet() {
        return tripSet;
    }

    public void setTripSet(Set<Trip> tripSet) {
        this.tripSet = tripSet;
    }

    public String toString() {
        return "Hotel: " + name + " , " + stars + " , " + description + " , " + city.getName();
    }

    public boolean equals(Object object) {
        if (object instanceof Hotel) {
            if (this.id == ((Hotel) object).getId() && this.name.equals(((Hotel) object).getName()) &&
                    this.stars == ((Hotel) object).getStars() &&
                    this.description.equals(((Hotel) object).getDescription()) &&
                    this.city.equals(((Hotel) object).getCity()) &&
                    this.roomSet.equals(((Hotel) object).getRoomSet())){
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(id, name, stars, description, city, roomSet);
    }
}
