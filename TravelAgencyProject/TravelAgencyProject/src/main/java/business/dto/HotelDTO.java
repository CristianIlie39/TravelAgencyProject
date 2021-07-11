package business.dto;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Set;

public class HotelDTO {

    @NotNull(message = "The name field cannot be null!")
    @NotEmpty(message = "The name field cannot be empty!")
    @NotBlank(message = "The name field cannot be blank!")
    @Pattern(regexp = "([a-z A-Z])*")
    private String name;

    @NotNull @Min(1) @Max(7)
    private int stars;

    @NotNull(message = "The description field cannot be null!")
    @NotEmpty(message = "The description field cannot be empty!")
    @NotBlank(message = "The description field cannot be blank!")
    private String description;

    @NotNull @Valid
    private CityDTO cityDTO;

    @NotEmpty @Valid
    private Set<RoomDTO> roomDTOSet;

    public HotelDTO(String name, int stars, String description, CityDTO cityDTO, Set<RoomDTO> roomDTOSet) {
        this.name = name;
        this.stars = stars;
        this.description = description;
        this.cityDTO = cityDTO;
        this.roomDTOSet = roomDTOSet;
    }

    public HotelDTO() {

    }

    public String getName() {
        return name;
    }

    public int getStars() {
        return stars;
    }

    public String getDescription() {
        return description;
    }

    public CityDTO getCityDTO() {
        return cityDTO;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCityDTO(CityDTO cityDTO) {
        this.cityDTO = cityDTO;
    }

    public Set<RoomDTO> getRoomDTOSet() {
        return roomDTOSet;
    }

    public void setRoomDTOSet(Set<RoomDTO> roomDTOSet) {
        this.roomDTOSet = roomDTOSet;
    }

    public String toString() {
        return "HotelDTO: " + name + " , " + stars + " , " + description + " , " + cityDTO.getName();
    }

}
