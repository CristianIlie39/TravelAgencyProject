package business.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ContinentDTO {

    @NotNull(message = "The name field cannot be null!")
    @NotEmpty(message = "The name field cannot be empty! Please enter the name of the continent.")
    @NotBlank(message = "The name field cannot be blank! Please enter the name of the continent!")
    @Pattern(regexp = "([a-z A-Z])*", message = "Please enter letters!")
    private String name;

    public ContinentDTO(String name) {
        this.name = name;
    }

    public ContinentDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ContinentDTO: " + name;
    }
}
