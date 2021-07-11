package business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Date;

public class PurchaseDTO {

    @NotNull(message = "The dateOfPurchase field cannot be null!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfPurchase;

    @NotNull
    private double amount;

    @NotNull @Valid
    private TripDTO tripDTO;

    @NotNull @Valid
    private ClientDTO clientDTO;

    public PurchaseDTO(Date dateOfPurchase, double amount, TripDTO tripDTO, ClientDTO clientDTO) {
        this.dateOfPurchase = dateOfPurchase;
        this.amount = amount;
        this.tripDTO = tripDTO;
        this.clientDTO = clientDTO;
    }

    public PurchaseDTO() {

    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public TripDTO getTripDTO() {
        return tripDTO;
    }

    public void setTripDTO(TripDTO tripDTO) {
        this.tripDTO = tripDTO;
    }

    public ClientDTO getClientDTO() {
        return clientDTO;
    }

    public void setClientDTO(ClientDTO clientDTO) {
        this.clientDTO = clientDTO;
    }

    public String toString() {
        return "PurchaseDTO: " + dateOfPurchase + " , " + amount + " , " + tripDTO.getName() + " , " +
                clientDTO.getSurname() + " " + clientDTO.getFirstName();
    }
}
