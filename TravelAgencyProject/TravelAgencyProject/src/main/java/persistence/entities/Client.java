package persistence.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@NamedQueries({

        @NamedQuery(name = "findClientByEmail", query = "SELECT client FROM Client client WHERE client.email = :email"),
        @NamedQuery(name = "findClientByUsername", query = "SELECT client FROM Client client WHERE " +
                "client.account.username = :username"),
        @NamedQuery(name = "findClientsByYearOfBirth", query = "SELECT client FROM Client client WHERE " +
                "client.yearOfBirth = :yearOfBirth"),
        @NamedQuery(name = "deleteClientByUsername", query = "DELETE FROM Client client WHERE client.account IN " +
                "(SELECT account FROM Account account WHERE username = :username)"),
        @NamedQuery(name = "findClientsBySurnameAndFirstName", query = "SELECT client FROM Client client WHERE " +
                "client.surname = :surname AND client.firstName = :firstName"),
        @NamedQuery(name = "findClientByUsernameAndPassword", query = "SELECT client FROM Client client WHERE " +
                "client.account.username = :username AND client.account.password = :password")

})

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "surname")
    private String surname;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "year_of_birth")
    private int yearOfBirth;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "accounts_id")
    private Account account;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private Set<Purchase> purchaseSet;

    public Client(String surname, String firstName, int yearOfBirth, String address, String phoneNumber,
                  String email, Account account) {
        this.surname = surname;
        this.firstName = firstName;
        this.yearOfBirth = yearOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.account = account;
    }

    public Client() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<Purchase> getPurchaseSet() {
        return purchaseSet;
    }

    public void setPurchaseSet(Set<Purchase> purchaseSet) {
        this.purchaseSet = purchaseSet;
    }

    public String toString() {
        return "Client: " + surname + " , " + firstName + " , " + yearOfBirth + " , " + address + " , " + phoneNumber +
                " , " + email + " , " + account.getUsername();
    }

    public boolean equals(Object object) {
        if (object instanceof Client) {
            if (this.id == ((Client) object).getId() && this.surname.equals(((Client) object).getSurname()) &&
                    this.firstName.equals(((Client) object).getFirstName()) &&
                    this.yearOfBirth == ((Client) object).getYearOfBirth() &&
                    this.address.equals(((Client) object).getAddress()) &&
                    this.phoneNumber.equals(((Client) object).getPhoneNumber()) &&
                    this.email.equals(((Client) object).getEmail()) &&
                    this.account.equals(((Client) object).getAccount())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(id, surname, firstName, yearOfBirth, address, phoneNumber, account);
    }

}
