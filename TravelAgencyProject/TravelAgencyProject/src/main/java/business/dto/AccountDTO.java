package business.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AccountDTO {

    @NotNull(message = "The username field cannot be null!")
    @NotEmpty(message = "The username field cannot be empty!")
    @NotBlank(message = "The username field cannot be blank!")
    private String username;

    @NotNull(message = "The password field cannot be null!")
    @NotEmpty(message = "The password field cannot be empty!")
    @NotBlank(message = "The password field cannot be blank!")
    private String password;

    @NotNull(message = "The adminRole field cannot be null!")
    private boolean adminRole;

    @NotNull(message = "The loggedIn field cannot be null!")
    private boolean loggedIn;

    public AccountDTO(String username, String password, boolean adminRole, boolean loggedIn) {
        this.username = username;
        this.password = password;
        this.adminRole = adminRole;
        this.loggedIn = loggedIn;
    }

    public AccountDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AccountDTO() {

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

    public String toString() {
        return "AccountDTO: " + username + " , admin role: " + adminRole + " , logged in: " + loggedIn;
    }

}
