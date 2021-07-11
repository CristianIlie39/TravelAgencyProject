package persistence.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@NamedQueries({
        @NamedQuery(name = "findFlight", query = "SELECT flight FROM Flight flight WHERE " +
                "flight.flightTo = :destination AND " +
                "flight.flightDepartureDate = :flightDepartureDate AND " +
                "flight.airport.name = :airportName"),
        @NamedQuery(name = "countFlights", query = "SELECT COUNT(*) FROM Flight flight WHERE " +
                "flight.flightTo = :destination AND flight.flightDepartureDate = :flightDepartureDate AND " +
                "flight.airport.name = :airportName"),
        @NamedQuery(name = "updateAvailableSeats", query = "UPDATE Flight flight SET " +
                "flight.availableSeats = availableSeats - :numberOfPersons WHERE " +
                "flight.flightTo = :destination AND flight.flightDepartureDate = :flightDepartureDate AND " +
                "flight.airport IN (SELECT airport FROM Airport airport WHERE airport.name = :airportName)"),
        @NamedQuery(name = "deleteFlight", query = "DELETE Flight flight WHERE " +
                "flight.flightTo = :destination AND flight.flightDepartureDate = :flightDepartureDate AND " +
                "flight.airport IN (SELECT airport FROM Airport airport WHERE airport.name = :airportName)")
})

@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "departure_date")
    private Date flightDepartureDate;

    @Column(name = "departure_time")
    private Timestamp flightDepartureTime;

    @Column(name = "return_date")
    private Date flightReturnDate;

    @Column(name = "return_time")
    private Timestamp flightReturnTime;

    @Column(name = "flight_to")
    private String flightTo;

    @Column(name = "price")
    private double price;

    @Column(name = "available_seats")
    private int availableSeats;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airports_id")
    private Airport airport;

    public Flight(Date flightDepartureDate, Timestamp flightDepartureTime, Date flightReturnDate,
                  Timestamp flightReturnTime, String flightTo, double price, int availableSeats) {
        this.flightDepartureDate = flightDepartureDate;
        this.flightDepartureTime = flightDepartureTime;
        this.flightReturnDate = flightReturnDate;
        this.flightReturnTime = flightReturnTime;
        this.flightTo = flightTo;
        this.price = price;
        this.availableSeats = availableSeats;
    }

    public Flight() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public java.sql.Date getFlightDepartureDate() {
        return flightDepartureDate;
    }

    public void setFlightDepartureDate(java.sql.Date flightDepartureDate) {
        this.flightDepartureDate = flightDepartureDate;
    }

    public Timestamp getFlightDepartureTime() {
        return flightDepartureTime;
    }

    public void setFlightDepartureTime(Timestamp flightDepartureTime) {
        this.flightDepartureTime = flightDepartureTime;
    }

    public java.sql.Date getFlightReturnDate() {
        return flightReturnDate;
    }

    public void setFlightReturnDate(java.sql.Date flightReturnDate) {
        this.flightReturnDate = flightReturnDate;
    }

    public Timestamp getFlightReturnTime() {
        return flightReturnTime;
    }

    public void setFlightReturnTime(Timestamp flightReturnTime) {
        this.flightReturnTime = flightReturnTime;
    }

    public String getFlightTo() {
        return flightTo;
    }

    public void setFlightTo(String flightTo) {
        this.flightTo = flightTo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public String toString() {
        return "Flight: " + flightDepartureDate + " , " + flightDepartureTime + " , " + flightReturnDate + " , " +
                flightReturnTime + " , " + flightTo + " , " + price + " , " + availableSeats + " , " + airport.getName();
    }

    public boolean equals(Object object) {
        if (object instanceof Flight) {
            if (this.id == ((Flight) object).getId() &&
                    this.flightDepartureDate.equals(((Flight) object).getFlightDepartureDate()) &&
                    this.flightDepartureTime.equals(((Flight) object).getFlightDepartureTime()) &&
                    this.flightReturnDate.equals(((Flight) object).getFlightReturnDate()) &&
                    this.flightReturnTime.equals(((Flight) object).getFlightReturnTime()) &&
                    this.flightTo.equals(((Flight) object).getFlightTo()) &&
                    this.price == ((Flight) object).getPrice() &&
                    this.availableSeats == ((Flight) object).getAvailableSeats()) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(id, flightDepartureDate, flightDepartureTime, flightReturnDate, flightReturnTime, flightTo,
                price, availableSeats);
    }
}
