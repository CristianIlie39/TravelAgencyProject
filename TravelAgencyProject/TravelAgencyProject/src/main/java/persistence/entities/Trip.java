package persistence.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

@NamedQueries({
        @NamedQuery(name = "findByName", query = "SELECT trip FROM Trip trip WHERE trip.name = :name"),
        @NamedQuery(name = "countTripByName", query = "SELECT COUNT (*) FROM Trip trip WHERE trip.name = :name"),
        @NamedQuery(name = "findAllTrips", query = "SELECT trip FROM Trip trip"),
        @NamedQuery(name = "findTripsByContinentName", query = "SELECT trip FROM Trip trip WHERE " +
                "trip.hotel.city.country.continent.name = :continentName"),
        @NamedQuery(name = "findTripsByCountryName", query = "SELECT trip FROM Trip trip WHERE " +
                "trip.hotel.city.country.name = :countryName"),
        @NamedQuery(name = "findTripsByCity", query = "SELECT trip FROM Trip trip WHERE trip.hotel.city.name = :cityName"),
        @NamedQuery(name = "findTripsByHotel", query = "SELECT trip FROM Trip trip WHERE trip.hotel.name = :hotelName"),
        @NamedQuery(name = "findTripsByCityOfDeparture", query = "SELECT trip FROM Trip trip WHERE " +
                "trip.airport.city.name = :cityDepartureName"),
        @NamedQuery(name = "findTripsByAirportName", query = "SELECT trip FROM Trip trip WHERE " +
                "trip.airport.name = :airportName"),
        @NamedQuery(name = "findTripsByDepartureDate", query = "SELECT trip FROM Trip trip WHERE " +
                "trip.departureDate = :departureDate"),
        @NamedQuery(name = "findTripsByReturnDate", query = "SELECT trip FROM Trip trip WHERE trip.returnDate = :returnDate"),
        @NamedQuery(name = "findTripsByMealType", query = "SELECT trip FROM Trip trip WHERE trip.mealType = :mealType"),
        @NamedQuery(name = "findTripsByStandardOfHotel", query = "SELECT trip FROM Trip trip WHERE trip.hotel.stars = :stars"),
        @NamedQuery(name = "findTripsByNumberOfDays", query = "SELECT trip FROM Trip trip WHERE trip.numberDays = :numberDays"),
        @NamedQuery(name = "findTripsByPromoted", query = "SELECT trip FROM Trip trip WHERE trip.promoted = :promoted"),
        @NamedQuery(name = "findTripsByMaximPriceForAdult", query = "SELECT trip FROM Trip trip WHERE " +
                "trip.adultPrice <= :maxPriceForAdult AND trip.kidPrice <= :maxPriceForKid"),
        @NamedQuery(name = "deleteTrip", query = "DELETE Trip trip WHERE trip.name = :name"),
        @NamedQuery(name = "updateTripStock", query = "UPDATE Trip trip SET trip.stock = :stock - 1"),
        @NamedQuery(name = "updateAdultPriceAndKidPriceByTripName", query = "UPDATE Trip trip SET " +
                "trip.adultPrice = :adultPrice, trip.kidPrice = :kidPrice WHERE trip.name = :name")
})

@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "airports_id")
    private Airport airport;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "hotels_id")
    private Hotel hotel;

    @Column(name = "departure_date")
    private Date departureDate;

    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "number_days")
    private int numberDays;

    @Column(name = "meal_type")
    private String mealType;

    @Column(name = "adult_price")
    private double adultPrice;

    @Column(name = "kid_price")
    private double kidPrice;

    @Column(name = "promoted")
    private boolean promoted;

    @Column(name = "stock")
    private int stock;

    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY)
    private Set<Purchase> purchaseSet;

    public Trip(String name, Airport airport, Hotel hotel, Date departureDate, Date returnDate, int numberDays,
                String mealType, double adultPrice, double kidPrice, boolean promoted, int stock) {
        this.name = name;
        this.airport = airport;
        this.hotel = hotel;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.numberDays = numberDays;
        this.mealType = mealType;
        this.adultPrice = adultPrice;
        this.kidPrice = kidPrice;
        this.promoted = promoted;
        this.stock = stock;
    }

    public Trip() {

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

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getNumberDays() {
        return numberDays;
    }

    public void setNumberDays(int numberDays) {
        this.numberDays = numberDays;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public double getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(double adultPrice) {
        this.adultPrice = adultPrice;
    }

    public double getKidPrice() {
        return kidPrice;
    }

    public void setKidPrice(double kidPrice) {
        this.kidPrice = kidPrice;
    }

    public boolean isPromoted() {
        return promoted;
    }

    public void setPromoted(boolean promoted) {
        this.promoted = promoted;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Set<Purchase> getPurchaseSet() {
        return purchaseSet;
    }

    public void setPurchaseSet(Set<Purchase> purchaseSet) {
        this.purchaseSet = purchaseSet;
    }

    public String toString() {
        return "Trip: " + name + " , " + airport.getName() + " , " + hotel.getName() + " , " + departureDate + " , " +
                returnDate + " , " + numberDays + " , " + mealType + " , " + adultPrice + " , " + kidPrice + " , " +
                promoted + " , " + stock;
    }

    public boolean equals(Object object) {
        if (object instanceof Trip) {
            if (this.id == ((Trip) object).getId() && this.name.equals(((Trip) object).getName()) &&
                    this.airport.equals(((Trip) object).getAirport()) &&
                    this.hotel.equals(((Trip) object).getHotel()) &&
                    this.departureDate.equals(((Trip) object).getDepartureDate()) &&
                    this.returnDate.equals(((Trip) object).getReturnDate()) &&
                    this.numberDays == ((Trip) object).getNumberDays() &&
                    this.mealType.equals(((Trip) object).getMealType()) &&
                    this.adultPrice == ((Trip) object).getAdultPrice() &&
                    this.kidPrice == ((Trip) object).getKidPrice() && this.promoted == ((Trip) object).isPromoted() &&
                    this.stock == ((Trip) object).getStock()) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(id, name, airport, hotel, departureDate, returnDate, numberDays, mealType, adultPrice,
                kidPrice, promoted, stock);
    }
}