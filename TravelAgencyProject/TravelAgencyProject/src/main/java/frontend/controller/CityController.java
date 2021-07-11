package frontend.controller;

import business.dto.CityDTO;
import business.dto.ClientDTO;
import business.service.CityService;
import business.service.ClientService;
import business.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class CityController {

    @Autowired
    CityService cityService;
    @Autowired
    CountryService countryService;
    @Autowired
    ClientService clientService;

    @PostMapping(path = "/insertCity")
    public ResponseEntity insertCity(@RequestBody @Valid CityDTO cityDTO, @RequestParam String adminUser) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            System.out.println("User " + adminUser + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            if (cityService.countCitiesByName(cityDTO.getName()) != 0) {
                System.out.println("The city " + cityDTO.getName() + " already exists in database.");
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("The city " + cityDTO.getName() + " already exists in database.");
            } else {
                cityService.insert(cityDTO);
                System.out.println("The city " + cityDTO.getName() + " has been added to the database.");
                return ResponseEntity.ok("The city " + cityDTO.getName() + " has been added to the database.");
            }
        } else {
            System.out.println("You are not logged in as an administrator, so you are not authorised to do this operation!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }

    @GetMapping(path = "/findCity")
    public ResponseEntity findCityByName(@RequestParam String cityDTOName) {
        CityDTO cityDTOFound = cityService.findCityDTOByName(cityDTOName);
        if (cityDTOFound == null) {
            System.out.println(cityDTOName + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cityDTOName + " does not exist in the database.");
        } else {
            System.out.println(cityDTOFound.toString());
            return ResponseEntity.ok(cityDTOFound);
        }
    }

    @DeleteMapping(path = "/deleteCity")
    public ResponseEntity deleteCityByName(@RequestParam String name, @RequestParam String adminUser) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            System.out.println("User " + adminUser + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            Integer numberOfCitiesDeleted = cityService.deleteCityDTOByName(name);
            if (numberOfCitiesDeleted == 0) {
                System.out.println(name + " does not exist in the database.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(name + " does not exist in the database.");
            } else {
                System.out.println(name + " has been deleted from the database.");
                return ResponseEntity.ok(name + " has been deleted from the database.");
            }
        } else {
            System.out.println("You are not logged in as an administrator, so you are not authorised to do this operation!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }

    @GetMapping(path = "/findCitiesByCountryName")
    public ResponseEntity findCitiesByCountryName(@RequestParam String countryName) {
        if (countryService.countCountriesByName(countryName) == 0) {
            System.out.println(countryName + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(countryName + " does not exist in the database.");
        }
        List<CityDTO> cityDTOList = cityService.findCitiesDTOByCountryDTOName(countryName);
        if (cityDTOList.isEmpty()) {
            System.out.println("No cities found for " + countryName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No cities found for " + countryName);
        } else {
            System.out.println("Cities from " + countryName + " are displayed.");
            return ResponseEntity.ok(cityDTOList);
        }
    }

    @GetMapping(path = "/findAllCities")
    public ResponseEntity findAllCities() {
        List<CityDTO> cityDTOList = cityService.findAllCities();
        if (cityDTOList.isEmpty()) {
            System.out.println("There are no cities in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no cities in the database.");
        } else {
            System.out.println("Cities are displayed.");
            return ResponseEntity.ok(cityDTOList);
        }
    }

    @PutMapping(path = "/updateCityName")
    public ResponseEntity updateCityName(@RequestParam String currentName, @RequestParam String newName, @RequestParam String adminUser) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            System.out.println("User " + adminUser + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            if (cityService.countCitiesByName(newName) != 0) {
                System.out.println(newName + " already exists in the database.");
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(newName + " already exists in the database.");
            } else {
                Integer numberOfCitiesUpdated = cityService.updateCityName(currentName, newName);
                if (numberOfCitiesUpdated == 0) {
                    System.out.println(currentName + " does not exist in the database.");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(currentName + " does not exist in the database.");
                } else {
                    System.out.println(currentName + " was changed in " + newName);
                    return ResponseEntity.ok(currentName + " was changed in " + newName);
                }
            }
        } else {
            System.out.println("You are not logged in as an administrator, so you are not authorised to do this operation!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }
}
