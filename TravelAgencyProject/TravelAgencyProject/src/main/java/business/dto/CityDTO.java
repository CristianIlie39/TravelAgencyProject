package business.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CityDTO {

    @NotNull(message = "The name field cannot be null!")
    @NotEmpty(message = "The name field cannot be empty!")
    @NotBlank(message = "The name field cannot be blank!")
    @Pattern(regexp = "([A-Z, a-z])*", message = "Please enter letters!")
    private String name;

    @NotNull @Valid
    private CountryDTO countryDTO;

    public CityDTO(String name, CountryDTO countryDTO) {
        this.name = name;
        this.countryDTO = countryDTO;
    }

    public CityDTO() {

    }

    public String getName() {
        return name;
    }

    public CountryDTO getCountryDTO() {
        return countryDTO;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountryDTO(CountryDTO countryDTO) {
        this.countryDTO = countryDTO;
    }

    @Override
    public String toString() {
        return "CityDTO: " + name + " , " + countryDTO.getName();
    }
}
