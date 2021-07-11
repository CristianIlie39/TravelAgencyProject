package persistence.entities;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries({
        @NamedQuery(name = "deleteAccountByUsername", query = "DELETE Account account WHERE account.username = :username"),
        @NamedQuery(name = "updateLogInUser", query = "UPDATE Account account SET account.loggedIn = :loggedIn WHERE " +
                "account.username = :username"),
        @NamedQuery(name = "changeUsername", query = "UPDATE Account account SET account.username = :newUsername WHERE " +
                "account.username = :currentUsername")
})

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "admin_role")
    private boolean adminRole;

    @Column(name = "logged_in")
    private boolean loggedIn;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private Client client;

    public Account(String username, String password, boolean adminRole, boolean loggedIn) {
        this.username = username;
        this.password = password;
        this.adminRole = adminRole;
        this.loggedIn = loggedIn;
    }

    public Account(String username, String password){
        this.username = username;
        this.password = password;
    }

    public Account() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdminRole() {
        return adminRole;
    }

    public void setAdminRole(boolean adminRole) {
        this.adminRole = adminRole;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String toString() {
        return "Account: " + username + " , " + adminRole + " , " + loggedIn;
    }

    public boolean equals(Object object) {
        if (object instanceof Account) {
            if (this.id == ((Account) object).getId() && this.username.equals(((Account) object).getUsername()) &&
                    this.password.equals(((Account) object).getPassword()) &&
                    this.adminRole == ((Account) object).isAdminRole() &&
                    this.loggedIn == ((Account) object).isLoggedIn() &&
                    this.client.equals(((Account) object).getClient())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(id, username, password, adminRole, loggedIn, client);
    }
}

