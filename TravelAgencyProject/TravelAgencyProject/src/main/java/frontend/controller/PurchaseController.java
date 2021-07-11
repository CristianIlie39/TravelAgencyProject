package frontend.controller;

import business.dto.*;
import business.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Date;
import java.util.List;

@RestController
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;
    @Autowired
    ClientService clientService;
    @Autowired
    TripService tripService;
    @Autowired
    RoomService roomService;
    @Autowired
    FlightService flightService;

    //only the client logged in to the application have acces to the endpoint below
    @PostMapping(path = "/purchaseTrip")
    public ResponseEntity insertPurchase(@RequestParam String username, @RequestParam String tripName, @RequestParam int numberOfAdults,
                                         @RequestParam int numberOfChildren, @RequestParam int singleRooms, @RequestParam int doubleRooms,
                                         @RequestParam int familyRooms, @RequestParam int apartments, @RequestParam Date flightDepartureDate) {
        ClientDTO clientDTO = clientService.findClientByUsername(username);
        if (clientDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + username + " does not exist in the database.");
        }
        if (!clientDTO.getAccountDTO().isLoggedIn()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not logged in!");
        }
        TripDTO tripDTO = tripService.findTripByName(tripName);
        if (tripDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The trip with name " + tripName + " does not exist in the database.");
        }
        if (!tripService.checkTripAvailability(tripDTO)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Trip sold out!");
        }
        if (!roomService.checkSingleRoomAvailability(tripDTO, singleRooms)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Single rooms are not available.");
        }
        if (!roomService.checkDoubleRoomAvailability(tripDTO, doubleRooms)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Double rooms are not available.");
        }
        if (!roomService.checkFamilyRoomAvailability(tripDTO, familyRooms)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Family rooms are not available.");
        }
        if (!roomService.checkApartmentAvailability(tripDTO, apartments)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Apartments are not available.");
        }
        FlightDTO flightDTO = flightService.findFlightByDestinationAndByDepartureDateAndByAirport(tripDTO.getHotelDTO().getCityDTO().getName(), flightDepartureDate, tripDTO.getAirportDTO().getName());
        if (flightDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no fights available to " + tripDTO.getHotelDTO().getCityDTO().getName());
        }
        if (!flightService.checkAvalabilityFlightSeats(flightDTO.getAvailableSeats(), (numberOfAdults + numberOfChildren))) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The are no seats available on the plane.");
        }
        PurchaseDTO purchaseDTO = new PurchaseDTO();
        purchaseDTO.setClientDTO(clientDTO);
        purchaseDTO.setTripDTO(tripDTO);
        purchaseService.insert(purchaseDTO, numberOfAdults, numberOfChildren, flightDepartureDate); //se achizitioneaza un trip
        tripService.updateTripStock(tripDTO.getStock()); //updatez stocul de tripuri dupa ce se achizitioneaza un trip
        flightService.updateAvailableSeatsNumber((numberOfAdults + numberOfChildren), tripDTO.getHotelDTO().getCityDTO().getName(), flightDepartureDate, tripDTO.getAirportDTO().getName()); //updatez numarul de locuri disponibile in avion dupa ce se achizitioneaza un trip
        roomService.updateAvailableRooms(singleRooms, "single", tripDTO.getHotelDTO().getName()); //updatez numarul camerelor single disponibile in hotel dupa ce se achizitioneaza un trip
        roomService.updateAvailableRooms(doubleRooms, "double", tripDTO.getHotelDTO().getName()); //updatez numarul camerelor double disponibile in hotel dupa ce se achizitioneaza un trip
        roomService.updateAvailableRooms(familyRooms, "family", tripDTO.getHotelDTO().getName()); //updatez numarul camerelor family disponibile in hotel dupa ce se achizitioneaza un trip
        roomService.updateAvailableRooms(apartments, "apartment", tripDTO.getHotelDTO().getName()); //updatez numarul apartamentelor disponibile in hotel dupa ce se achizitioneaza un trip
        System.out.println("You have successfully purchased your trip! Thank you!");
        return ResponseEntity.ok("You have successfully purchased your trip! Thank you!");
    }

    //only the administrator logged in to the application have acces to the endpoint below
    @GetMapping(path = "/findRecentlyPurchases")
    public ResponseEntity findRecentlyPurchases(@RequestParam String adminUser, @RequestParam Date referenceDate) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            List<PurchaseDTO> purchaseDTOList = purchaseService.findRecentlyPurchases(referenceDate);
            if (purchaseDTOList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no purchases in the reference range.");
            } else {
                return ResponseEntity.ok(purchaseDTOList);
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }

    //only the administrator logged in to the application have acces to the endpoint below
    @GetMapping(path = "/findPurchasesByUsername")
    public ResponseEntity findPurchasesByUsername(@RequestParam String adminUser, @RequestParam String username) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            ClientDTO user = clientService.findClientByUsername(username);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + username + " does not exist in the database.");
            }
            List<PurchaseDTO> purchaseDTOList = purchaseService.findPurchasesByUsername(username);
            if (purchaseDTOList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + username + " did not purchased any trip.");
            } else {
                return ResponseEntity.ok(purchaseDTOList);
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, so " +
                    "you are not authorised to do this operation!");
        }
    }

    @GetMapping(path = "/getTotalAmountSpendByUsername")
    public ResponseEntity getTotalAmountSpendByUsername(@RequestParam String adminUser, @RequestParam String username) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            ClientDTO user = clientService.findClientByUsername(username);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + username + " does not exist in the database.");
            }
            double amount = purchaseService.calculateTotalAmountSpendByClient(username);
            return ResponseEntity.ok("Total amount spend by " + username + " is " + amount + " RON.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, so " +
                    "you are not authorised to do this operation!");
        }
    }
}



