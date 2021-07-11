package persistence.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@NamedQueries({
        @NamedQuery(name = "findPurchaseByUsernameAndTripNameQuery", query = "SELECT purchase FROM Purchase purchase WHERE " +
                "purchase.client.account.username = :username"),
        @NamedQuery(name = "findRecentlyPurchasedTrips", query = "SELECT trip FROM Purchase purchase WHERE " +
                "(purchase.dateOfPurchase BETWEEN :referenceDate AND :currentDate)"),
        @NamedQuery(name = "findRecentlyPurchases", query = "SELECT purchase FROM Purchase purchase WHERE " +
                "(purchase.dateOfPurchase BETWEEN :referenceDate AND :currentDate)"),
        @NamedQuery(name = "findPurchasesByUsername", query = "SELECT purchase FROM Purchase purchase WHERE " +
                "purchase.client.account.username = :username")
})

@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "date_of_purchase")
    private Date dateOfPurchase;

    @Column(name = "amount")
    private double amount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trips_id")
    private Trip trip;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clients_id")
    private Client client;

    public Purchase(Date dateOfPurchase, double amount, Trip trip, Client client) {
        this.dateOfPurchase = dateOfPurchase;
        this.amount = amount;
        this.trip = trip;
        this.client = client;
    }

    public Purchase() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String toString() {
        return "Purchase: " + trip.getName() + " , " + dateOfPurchase + " , " + amount + " , " + client.getSurname() +
                " " + client.getFirstName();
    }

    public boolean equals(Object object) {
        if (object instanceof Purchase) {
            if (this.id == ((Purchase) object).getId() &&
                    this.dateOfPurchase.equals(((Purchase) object).getDateOfPurchase()) &&
                    this.amount == ((Purchase) object).getAmount() &&
                    this.trip.equals(((Purchase) object).getTrip()) &&
                    this.client.equals(((Purchase) object).getClient())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(id, dateOfPurchase, amount, trip, client);
    }
}
