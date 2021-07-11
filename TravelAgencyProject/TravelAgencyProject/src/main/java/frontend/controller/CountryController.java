package frontend.controller;

import business.dto.ClientDTO;
import business.dto.CountryDTO;
import business.service.ClientService;
import business.service.ContinentService;
import business.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class CountryController {

    @Autowired
    CountryService countryService;
    @Autowired
    ContinentService continentService;
    @Autowired
    ClientService clientService;

    @PostMapping(path = "/insertCountry")
    public ResponseEntity insert(@RequestBody @Valid CountryDTO countryDTO, @RequestParam String adminUser) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            System.out.println("User " + adminUser + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            if (countryService.countCountriesByName(countryDTO.getName()) != 0) {
                System.out.println("The country " + countryDTO.getName() + " already exists in database.");
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("The country " + countryDTO.getName() + " already exists in database.");
            }
            countryService.insert(countryDTO);
            System.out.println("The country " + countryDTO.getName() + " has been added to the database.");
            return ResponseEntity.ok("The country " + countryDTO.getName() + " has been added to the database.");
        } else {
            System.out.println("You are not logged in as an administrator, so you are not authorised to do this operation!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }

    @GetMapping(path = "/findCountry")
    public ResponseEntity findCountry(@RequestParam String name) {
        CountryDTO countryDTOFound = countryService.findCountryDTOByName(name);
        if (countryDTOFound == null) {
            System.out.println(name + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(name + " does not exist in the database.");
        }
        System.out.println(countryDTOFound.toString());
        return ResponseEntity.ok(countryDTOFound);
    }

    @GetMapping(path = "/findCountriesByContinentName")
    public ResponseEntity findCountriesByContinentName(@RequestParam String continentName) {
        if (continentService.countContinentsByName(continentName) == 0) {
            System.out.println(continentName + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(continentName + "does not exist in the database.");
        }
        List<CountryDTO> countryDTOList = countryService.findCountriesDTOByContinentDTOName(continentName);
        if (countryDTOList.isEmpty()) {
            System.out.println("No countries found for " + continentName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No countries found for " + continentName);
        }
        return ResponseEntity.ok(countryDTOList);
    }

    @DeleteMapping(path = "/deleteCountry")
    public ResponseEntity deleteCountryByName(@RequestParam String name, @RequestParam String adminUser) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            System.out.println("User " + adminUser + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            Integer numberOfCountryDeleted = countryService.deleteCountryDTOByName(name);
            if (numberOfCountryDeleted == 0) {
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

    @PutMapping(path = "/updateCountryName")
    public ResponseEntity updateCountryName(@RequestParam String currentName, @RequestParam String newName,
                                            @RequestParam String adminUser) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            System.out.println("User " + adminUser + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            if (countryService.countCountriesByName(newName) != 0) {
                System.out.println(newName + " already exists in the database.");
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(newName + " already exists in the database.");
            } else {
                Integer numberOfCountriesUpdated = countryService.updateCountryName(currentName, newName);
                if (numberOfCountriesUpdated == 0) {
                    System.out.println(currentName + " does not exist in the database.");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(currentName + " does not exist in the database.");
                } else {
                    System.out.println(currentName + " has been updated in " + newName);
                    return ResponseEntity.ok(currentName + " has been updated in " + newName);
                }
            }
        } else {
            System.out.println("You are not logged in as an administrator, so you are not authorised to do this operation!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }

    @GetMapping(path = "/findAllCountries")
    public ResponseEntity findAllCountries() {
        List<CountryDTO> countryDTOList = countryService.findAllCountries();
        if (countryDTOList.isEmpty()) {
            System.out.println("There are no countries in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no countries in the database.");
        } else {
            System.out.println("The countries are displayed.");
            return ResponseEntity.ok(countryDTOList);
        }
    }
}
