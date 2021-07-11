package business.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class AirportDTO {

    @NotNull(message = "The field name cannot be null!")
    @NotEmpty(message = "The field name cannot be empty!")
    @NotBlank(message = "The field name cannot be blank!")
    private String name;

    @NotNull @Valid
    private CityDTO cityDTO;

    @NotNull @Valid
    private Set<FlightDTO> flightDTOSet;

    public AirportDTO(String name, CityDTO cityDTO, Set<FlightDTO> flightDTOSet) {
        this.name = name;
        this.cityDTO = cityDTO;
        this.flightDTOSet = flightDTOSet;
    }

    public AirportDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CityDTO getCityDTO() {
        return cityDTO;
    }

    public void setCityDTO(CityDTO cityDTO) {
        this.cityDTO = cityDTO;
    }

    public Set<FlightDTO> getFlightDTOSet() {
        return flightDTOSet;
    }

    public void setFlightDTOSet(Set<FlightDTO> flightDTOSet) {
        this.flightDTOSet = flightDTOSet;
    }

    public String toString() {
        return "AirportDTO: " + name + " , " + cityDTO.getName();
    }

}
