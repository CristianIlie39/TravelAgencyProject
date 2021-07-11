package frontend.controller;

import business.dto.AirportDTO;
import business.dto.ClientDTO;
import business.service.AirportService;
import business.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class AirportController {

    @Autowired
    AirportService airportService;
    @Autowired
    ClientService clientService;

    @PostMapping(path = "/insertAirport")
    public ResponseEntity insert(@RequestBody @Valid AirportDTO airportDTO, @RequestParam String adminUser) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            System.out.println("User " + adminUser + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            if (airportService.countAirport(airportDTO.getName(), airportDTO.getCityDTO().getName()) != 0) {
                System.out.println(airportDTO.getName() + " already exists in the database.");
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(airportDTO.getName() + " already exists in the database.");
            }
            airportService.insert(airportDTO);
            System.out.println(airportDTO + " has been added to the database.");
            return ResponseEntity.ok(airportDTO.getName() + " has been added to the database.");
        } else {
            System.out.println("You are not logged in as an administrator, so you are not authorised to do this operation!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator," +
                    "so you are not authorised to do this operation!");
        }
    }

    @GetMapping(path = "/findAirportByName")
    public ResponseEntity findAirportByName(@RequestParam String airportName) {
        AirportDTO airportDTO = airportService.findAirportByName(airportName);
        if (airportDTO == null) {
            System.out.println(airportName + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(airportName + " does not exist in the database.");
        } else {
            System.out.println(airportDTO.toString());
            return ResponseEntity.ok(airportDTO);
        }
    }

    @GetMapping(path = "/findAirportsByCityName")
    public ResponseEntity findAirportsByCityName(@RequestParam String cityName) {
        List<AirportDTO> airportDTOList = airportService.findAirportsByCityName(cityName);
        if (airportDTOList.isEmpty()) {
            System.out.println(cityName + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cityName + " does not exist in the database.");
        } else {
            System.out.println("Airports are displayed.");
            return ResponseEntity.ok(airportDTOList);
        }
    }

    @PutMapping(path = "/updateAirportName")
    public ResponseEntity updateAirportName(@RequestParam String currentName, @RequestParam String newName, @RequestParam String adminUser) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            System.out.println("User " + adminUser + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            int numberOfAirportsUpdated = airportService.updateAirportName(currentName, newName);
            if (numberOfAirportsUpdated == 0) {
                System.out.println("No airport updated.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(currentName + " does not exist in the database. No airport updated.");
            } else {
                System.out.println(currentName + " was changed in " + newName);
                return ResponseEntity.ok(currentName + " was changed in " + newName);
            }
        } else {
            System.out.println("You are not logged in as an administrator, so you are not authorised to do this operation!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }

    }

    @DeleteMapping(path = "/deleteAirport")
    public ResponseEntity deleteAirportByName(@RequestParam String airportName, @RequestParam String adminUser) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            System.out.println("User " + adminUser + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            int numberOfAirportsDeleted = airportService.deleteAirportByName(airportName);
            if (numberOfAirportsDeleted == 0) {
                System.out.println(airportName + " does not exist in the database.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(airportName + " does not exist in the database.");
            } else {
                System.out.println(airportName + " has been deleted.");
                return ResponseEntity.ok(airportName + " has been deleted.");
            }
        } else {
            System.out.println("You are not logged in as an administrator, so you are not authorised to do this operation!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }
}
