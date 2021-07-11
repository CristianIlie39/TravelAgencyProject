package business.dto;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class ClientDTO {

    @NotNull(message = "The surname field cannot be null!")
    @NotEmpty(message = "The surname field cannot be empty!")
    @NotBlank(message = "The surname field cannot be blank!")
    @Pattern(regexp = "([a-z A-Z -])*", message = "Please enter letters!")
    private String surname;

    @NotNull(message = "The firstName field cannot be null!")
    @NotEmpty(message = "The firstName field cannot be empty!")
    @NotBlank(message = "The firstName field cannot be blank!")
    @Pattern(regexp = "([a-z A-Z -])*", message = "Please enter letters!")
    private String firstName;

    @NotNull
    private int yearOfBirth;

    @NotNull(message = "The address field cannot be null!")
    @NotEmpty(message = "The address field cannot be empty!")
    @NotBlank(message = "The address field cannot be blank!")
    private String address;

    @NotNull(message = "The phoneNumber field cannot be null!")
    @NotEmpty(message = "The phoneNumber field cannot be empty!")
    @NotBlank(message = "The phoneNumber field cannot be blank!")
    private String phoneNumber;

    @NotNull(message = "The email field cannot be null!")
    @NotEmpty(message = "The email field cannot be empty!")
    @NotBlank(message = "The email field cannot be blank!")
    @Email
    private String email;

    @NotNull @Valid
    private AccountDTO accountDTO;

    public ClientDTO(String surname, String firstName, int yearOfBirth, String address, String phoneNumber,
                     String email, AccountDTO accountDTO) {
        this.surname = surname;
        this.firstName = firstName;
        this.yearOfBirth = yearOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.accountDTO = accountDTO;
    }

    public ClientDTO() {

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

    public AccountDTO getAccountDTO() {
        return accountDTO;
    }

    public void setAccountDTO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
    }

    public String toString() {
        return "ClientDTO: " + surname + " , " + firstName + " , yearOfBirth: " + yearOfBirth + " , address: " + address + " , phoneNumber: " +
                phoneNumber + " , username: " + accountDTO.getUsername();
    }

}
