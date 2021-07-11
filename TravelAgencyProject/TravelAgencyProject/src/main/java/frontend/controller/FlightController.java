package frontend.controller;

import business.dto.ClientDTO;
import business.dto.FlightDTO;
import business.service.ClientService;
import business.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;

@RestController
public class FlightController {

    @Autowired
    FlightService flightService;
    @Autowired
    ClientService clientService;

    @GetMapping(path = "/findFlight")
    public ResponseEntity findFlight(@RequestParam String flightDestination, @RequestParam Date flightDepartureDate,
                                     @RequestParam String airportName) {
        FlightDTO flightDTO = flightService.findFlightByDestinationAndByDepartureDateAndByAirport(flightDestination,
                flightDepartureDate, airportName);
        if (flightDTO == null) {
            System.out.println("No flight found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No flight found");
        } else {
            System.out.println("The flight was found.");
            return ResponseEntity.ok(flightDTO);
        }
    }

    @PutMapping(path = "/updateAvailableSeats")
    public ResponseEntity updateAvailableSeats(@RequestParam int numberOfPersons, @RequestParam String destination,
                                               @RequestParam Date flightDepartureDate, @RequestParam String airportName,
                                               @RequestParam String adminUser) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            System.out.println("User " + adminUser + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            int numberOfSeatsAvailable = flightService.updateAvailableSeatsNumber(numberOfPersons, destination, flightDepartureDate, airportName);
            if (numberOfSeatsAvailable == 0) {
                System.out.println("There is no flights according to the data entered.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no flight according to the data entered.");
            } else {
                System.out.println("The number of available seats has been updated.");
                return ResponseEntity.ok("The number of available seats has been updated.");
            }
        } else {
            System.out.println("You are not logged in as an administrator, so you are not authorised to do this operation!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }

    @DeleteMapping(path = "/deleteFlight")
    public ResponseEntity deleteFlight(@RequestParam String destination, @RequestParam Date flightDepartureDate,
                                       @RequestParam String airportName, @RequestParam String adminUser) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            System.out.println("User " + adminUser + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            int numberOfFlightDeleted = flightService.deleteFlight(destination, flightDepartureDate, airportName);
            if (numberOfFlightDeleted == 0) {
                System.out.println("The flight does not exist in the data base.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The flight does not exist in the database.");
            } else {
                System.out.println("The flight has been deleted from the database.");
                return ResponseEntity.ok("The flight has been deleted from the database.");
            }
        } else {
            System.out.println("You are not logged in as an administrator, so you are not authorised to do this operation!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }
}
