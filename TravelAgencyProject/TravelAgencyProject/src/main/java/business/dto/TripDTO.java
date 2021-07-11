package business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;

public class TripDTO {

    @NotNull(message = "The name field cannot be null!")
    @NotEmpty(message = "The name field cannot be empty!")
    @NotBlank(message = "The name field cannot be blank!")
    @Pattern(regexp = "([a-z A-Z])*")
    private String name;

    @NotNull @Valid
    private AirportDTO airportDTO;

    @NotNull @Valid
    private HotelDTO hotelDTO;

    @NotNull(message = "The flightDepartureDate field cannot be null!")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date departureDate;

    @NotNull(message = "The flightDepartureDate field cannot be null!")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date returnDate;

    @NotNull(message = "The numberDays field cannot be null!")
    private int numberDays;

    @NotNull(message = "The mealType field cannot be null!")
    @NotEmpty(message = "The mealType field cannot be empty!")
    @NotBlank(message = "The mealType field cannot be blank!")
    @Pattern(regexp = "([a-z A-Z])*")
    private String mealType;

    @NotNull
    private double adultPrice;

    @NotNull
    private double kidPrice;

    @NotNull(message = "The promoted field cannot be null!")
    private boolean promoted;

    @NotNull
    private int stock;

    public TripDTO(String name, AirportDTO airportDTO, HotelDTO hotelDTO, Date departureDate, Date returnDate,
                   int numberDays, String mealType, double adultPrice, double kidPrice, boolean promoted, int stock) {
        this.name = name;
        this.airportDTO = airportDTO;
        this.hotelDTO = hotelDTO;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.numberDays = numberDays;
        this.mealType = mealType;
        this.adultPrice = adultPrice;
        this.kidPrice = kidPrice;
        this.promoted = promoted;
        this.stock = stock;
    }

    public TripDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AirportDTO getAirportDTO() {
        return airportDTO;
    }

    public void setAirportDTO(AirportDTO airportDTO) {
        this.airportDTO = airportDTO;
    }

    public HotelDTO getHotelDTO() {
        return hotelDTO;
    }

    public void setHotelDTO(HotelDTO hotelDTO) {
        this.hotelDTO = hotelDTO;
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

    @Override
    public String toString() {
        return "TripDTO: " + name +
                " , AirportDTO: " + airportDTO.getName() +
                " , HotelDTO: " + hotelDTO.getName() +
                " , departure date " + departureDate +
                " , return date " + returnDate +
                " , number of days " + numberDays +
                " , meal type " + mealType +
                " , price for adult " + adultPrice +
                " , price for kid " + kidPrice +
                " , promoted " + promoted +
                " , number of trip available " + stock;
    }
}
