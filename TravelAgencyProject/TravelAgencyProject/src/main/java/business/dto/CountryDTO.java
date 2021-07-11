package business.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CountryDTO {

    @NotNull(message = "The name field cannot be null!")
    @NotEmpty(message = "The name field cannot be empty!")
    @NotBlank(message = "The name field cannot be blank!")
    @Pattern(regexp = "([a-z A-Z])*", message = "Please enter letters!")
    private String name;

    @NotNull @Valid
    private ContinentDTO continentDTO;

    public CountryDTO(String name, ContinentDTO continentDTO) {
        this.name = name;
        this.continentDTO = continentDTO;
    }

    public CountryDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContinentDTO getContinentDTO() {
        return continentDTO;
    }

    public void setContinentDTO(ContinentDTO continentDTO) {
        this.continentDTO = continentDTO;
    }

    public String toString() {
        return "CountryDTO: " + name + " , " + continentDTO.getName();
    }

}
