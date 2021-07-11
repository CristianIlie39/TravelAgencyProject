package business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.sql.Timestamp;

public class FlightDTO {

    @NotNull(message = "The flightDepartureDate field cannot be null!")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date flightDepartureDate;

    @NotNull(message = "The flightDepartureTime field cannot be null!")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss",timezone = "UTC+2")
    private Timestamp flightDepartureTime;

    @NotNull(message = "The flightReturnDate field cannot be null!")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date flightReturnDate;

    @NotNull(message = "The flightDepartureTime field cannot be null!")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm:ss",timezone = "UTC+2")
    private Timestamp flightReturnTime;

    @NotNull(message = "The flightTo field cannot be null!")
    @NotEmpty(message = "The flightTo field cannot be empty!")
    @NotBlank(message = "The flightTo field cannot be blank!")
    @Pattern(regexp = "([a-z A-Z])*")
    private String flightTo;

    @NotNull
    private double price;

    @NotNull
    private int availableSeats;

    public FlightDTO(Date flightDepartureDate, Timestamp flightDepartureTime, Date flightReturnDate,
                     Timestamp flightReturnTime, String flightTo, double price, int availableSeats) {
        this.flightDepartureDate = flightDepartureDate;
        this.flightDepartureTime = flightDepartureTime;
        this.flightReturnDate = flightReturnDate;
        this.flightReturnTime = flightReturnTime;
        this.flightTo = flightTo;
        this.price = price;
        this.availableSeats = availableSeats;
    }

    public FlightDTO() {

    }

    public Date getFlightDepartureDate() {
        return flightDepartureDate;
    }

    public void setFlightDepartureDate(Date flightDepartureDate) {
        this.flightDepartureDate = flightDepartureDate;
    }

    public Timestamp getFlightDepartureTime() {
        return flightDepartureTime;
    }

    public void setFlightDepartureTime(Timestamp flightDepartureTime) {
        this.flightDepartureTime = flightDepartureTime;
    }

    public Date getFlightReturnDate() {
        return flightReturnDate;
    }

    public void setFlightReturnDate(Date flightReturnDate) {
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

    public String toString() {
        return "FlightDTO: " + flightDepartureDate + " , " + flightDepartureTime + " , " + flightReturnDate + " , " +
                flightReturnTime + " , " + flightTo + " , " + price + " , " + availableSeats;
    }
}
