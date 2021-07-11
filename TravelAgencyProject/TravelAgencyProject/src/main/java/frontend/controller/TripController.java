package frontend.controller;

import business.dto.ClientDTO;
import business.dto.TripDTO;
import business.service.ClientService;
import business.service.TripService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.List;

@RestController
public class TripController {

    @Autowired
    TripService tripService;
    @Autowired
    ClientService clientService;

    //only the administrator logged in to the application have acces to the endpoint below
    @PostMapping(path = "/insertTrip")
    public ResponseEntity insertTrip(@RequestBody @Valid TripDTO tripDTO, @RequestParam String adminUser) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            System.out.println("User " + adminUser + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            long numberOfTrips = tripService.countTripByName(tripDTO.getName());
            if (numberOfTrips != 0) {
                System.out.println(tripDTO.getName() + " already exists in the database.");
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(tripDTO.getName() + " already exists in the database.");
            } else {
                tripService.insert(tripDTO);
                System.out.println(tripDTO.getName() + " has been added to the database.");
                return ResponseEntity.ok(tripDTO.getName() + " has been added to the database.");
            }
        } else {
            System.out.println("You are not logged in as an administrator, so you are not authorised to do this operation!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }

    //here I enter a complete list of trips from a file
    @PostMapping(path = "/insertTripList")
    public ResponseEntity insertTrip(@RequestParam String adminUser, @RequestParam("file") MultipartFile multipartFile) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            System.out.println("User " + adminUser + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            try {
                InputStream inputStream = multipartFile.getInputStream();
                XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
                tripService.insertTripDTOList(workbook);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("The Trip list has been added to the database.");
            return ResponseEntity.ok("The Trip list has been added to the database.");
        } else {
            System.out.println("You are not logged in as an administrator, so you are not authorised to do this operation!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }

    @GetMapping(path = "/findTripByName")
    public ResponseEntity findTripByName(@RequestParam String tripName) {
        TripDTO tripDTOFound = tripService.findTripByName(tripName);
        if (tripDTOFound == null) {
            System.out.println(tripName + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(tripName + " does not exists in the database.");
        } else {
            System.out.println(tripName + " was found and displayed.");
            return ResponseEntity.ok(tripDTOFound);
        }
    }

    @GetMapping(path = "/findAllTrips")
    public ResponseEntity findAllTrips() {
        List<TripDTO> tripDTOList = tripService.findAllTrips();
        if (tripDTOList.isEmpty()) {
            System.out.println("There are no trips in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no trips in the database.");
        } else {
            System.out.println("Trips are displayed.");
            return ResponseEntity.ok(tripDTOList);
        }
    }

    @GetMapping(path = "/findTripsByContinentName")
    public ResponseEntity findTripsByContinentName(@RequestParam String continentName) {
        List<TripDTO> tripDTOList = tripService.findTripsByContinentName(continentName);
        if (tripDTOList.isEmpty()) {
            System.out.println("There are no trips to " + continentName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no trips to " + continentName);
        } else {
            System.out.println("Trips to " + continentName + " are displayed");
            return ResponseEntity.ok(tripDTOList);
        }
    }

    @GetMapping(path = "/findTripsByCountryName")
    public ResponseEntity findTripsByCountryName(@RequestParam String countryName) {
        List<TripDTO> tripDTOList = tripService.findTripsByCountryName(countryName);
        if (tripDTOList.isEmpty()) {
            System.out.println("There are no trips to " + countryName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no trips to " + countryName);
        } else {
            System.out.println("Trips to " + countryName + " are displayed.");
            return ResponseEntity.ok(tripDTOList);
        }
    }

    @GetMapping(path = "/findTripsByCityOfStay")
    public ResponseEntity findTripsByCityOfStay(@RequestParam String cityName) {
        List<TripDTO> tripDTOList = tripService.findTripsByCityOfStay(cityName);
        if (tripDTOList.isEmpty()) {
            System.out.println("There are no trips to " + cityName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no trips to " + cityName);
        } else {
            System.out.println("Trips to " + cityName + " are displayed.");
            return ResponseEntity.ok(tripDTOList);
        }
    }

    @GetMapping(path = "/findByHotelOfStay")
    public ResponseEntity findByHotelOfStay(@RequestParam String hotelName) {
        List<TripDTO> tripDTOList = tripService.findByHotelOfStay(hotelName);
        if (tripDTOList.isEmpty()) {
            System.out.println("There are no trips to " + hotelName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no trips to " + hotelName);
        } else {
            System.out.println("Trips to " + hotelName + " are displayed.");
            return ResponseEntity.ok(tripDTOList);
        }
    }

    @GetMapping(path = "/findTripsByCityOfDeparture")
    public ResponseEntity findTripsByCityOfDeparture(@RequestParam String cityDepartureName) {
        List<TripDTO> tripDTOList = tripService.findTripsByCityOfDeparture(cityDepartureName);
        if (tripDTOList.isEmpty()) {
            System.out.println("There are no trips from " + cityDepartureName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no trips from " + cityDepartureName);
        } else {
            System.out.println("Trips from " + cityDepartureName + " are displayed.");
            return ResponseEntity.ok(tripDTOList);
        }
    }

    @GetMapping(path = "/findTripsByAirport")
    public ResponseEntity findTripsByAirport(@RequestParam String airportName) {
        List<TripDTO> tripDTOList = tripService.findTripsByAirport(airportName);
        if (tripDTOList.isEmpty()) {
            System.out.println("There are no trips from " + airportName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no trips from " + airportName);
        } else {
            System.out.println("Trips from " + airportName + " are displayed.");
            return ResponseEntity.ok(tripDTOList);
        }
    }

    @GetMapping(path = "/findTripsByDepartureDate")
    public ResponseEntity findTripsByDepartureDate(@RequestParam Date departure) {
        List<TripDTO> tripDTOList = tripService.findTripsByDepartureDate(departure);
        if (tripDTOList.isEmpty()) {
            System.out.println("There are no trips departing from " + departure);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no trips departing from " + departure);
        } else {
            System.out.println("Trips departing from " + departure + " are displayed.");
            return ResponseEntity.ok(tripDTOList);
        }
    }

    @GetMapping(path = "/findTripsByReturnDate")
    public ResponseEntity findTripsByReturnDate(@RequestParam Date returnDate) {
        List<TripDTO> tripDTOList = tripService.findTripsByReturnDate(returnDate);
        if (tripDTOList.isEmpty()) {
            System.out.println("There are no return trips on " + returnDate);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no return trips on " + returnDate);
        } else {
            System.out.println("Trips returning on " + returnDate + " are displayed.");
            return ResponseEntity.ok(tripDTOList);
        }
    }

    @GetMapping(path = "/findTripsByMealType")
    public ResponseEntity findTripsByMealType(@RequestParam String mealType) {
        List<TripDTO> tripDTOList = tripService.findTripsByMealType(mealType);
        if (tripDTOList.isEmpty()) {
            System.out.println("There are no trips with " + mealType);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no trips with " + mealType);
        } else {
            System.out.println("Trips with " + mealType + " are displayed");
            return ResponseEntity.ok(tripDTOList);
        }
    }

    @GetMapping(path = "/findTripsByStandardOfHotel")
    public ResponseEntity findTripsByStandardOfHotel(@RequestParam int hotelStars) {
        List<TripDTO> tripDTOList = tripService.findTripsByStandardOfHotel(hotelStars);
        if (tripDTOList.isEmpty()) {
            System.out.println("There are no trips with " + hotelStars + " star hotel accommodation.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no trips with " + hotelStars + " star hotel accommodation.");
        } else {
            System.out.println("Trips with " + hotelStars + " star hotel accommodation are displayed.");
            return ResponseEntity.ok(tripDTOList);
        }
    }

    @GetMapping(path = "/findTripsByNumberOfDays")
    public ResponseEntity findTripsByNumberOfDays(@RequestParam int numberDays) {
        List<TripDTO> tripDTOList = tripService.findTripsByNumberOfDays(numberDays);
        if (tripDTOList.isEmpty()) {
            System.out.println("There are no " + numberDays + " day trips.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no " + numberDays + " day trips.");
        } else {
            System.out.println(numberDays + " day trips are displayed.");
            return ResponseEntity.ok(tripDTOList);
        }
    }

    @GetMapping(path = "/findTripsByPromoted")
    public ResponseEntity findTripsByPromoted(@RequestParam boolean promoted) {
        List<TripDTO> tripDTOList = tripService.findTripsByPromoted(promoted);
        if (tripDTOList.isEmpty()) {
            System.out.println("There are no promoted trips.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no promoted trips.");
        } else {
            System.out.println("Promoted trips are displayed.");
            return ResponseEntity.ok(tripDTOList);
        }
    }

    @GetMapping(path = "/findTripsByMaximPrice")
    public ResponseEntity findTripsByMaximPrice(@RequestParam double maxPriceForAdult, @RequestParam double maxPriceForKid) {
        List<TripDTO> tripDTOList = tripService.findTripsByMaximPrice(maxPriceForAdult, maxPriceForKid);
        if (tripDTOList.isEmpty()) {
            System.out.println("There are no trips with lower prices.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no trips with the maximum prices extered.");
        } else {
            System.out.println("Trips with a price less than " + maxPriceForAdult + " for adult and less than " +
                    maxPriceForKid + " for kid are displayed.");
            return ResponseEntity.ok(tripDTOList);
        }
    }

    //only the administrator logged in to the application have acces to the endpoint below
    @PutMapping(path = "/updateAdultPriceAndKidPriceByTripName")
    public ResponseEntity updateAdultPriceAndKidPriceByTripName(@RequestParam String adminUser, @RequestParam double adultPrice,
                                                                @RequestParam double kidPrice, @RequestParam String tripName) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            int updateAdultPriceAndKidPriceByTripName = tripService.updateAdultPriceAndKidPriceByTripName(adultPrice, kidPrice, tripName);
            if (updateAdultPriceAndKidPriceByTripName == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Trip with name " + tripName + " does not exist in the database.");
            } else {
                return ResponseEntity.ok("Adult price and kid price have been changed in the database for trip " + tripName);
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }

    //only the administrator logged in to the application have acces to the endpoint below
    @DeleteMapping(path = "/deleteTrip")
    public ResponseEntity deleteTripByName(@RequestParam String tripName, @RequestParam String adminUser) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            System.out.println("User " + adminUser + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            int numberOfTripDeleted = tripService.deleteTripByName(tripName);
            if (numberOfTripDeleted == 0) {
                System.out.println("The trip with name " + tripName + " does not exist in the database.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The trip with name " + tripName + " does not exist in the database.");
            } else {
                System.out.println("The trip with name " + tripName + " has been deleted from the database.");
                return ResponseEntity.ok("The trip with name " + tripName + " has been deleted from the database.");
            }
        } else {
            System.out.println("You are not logged in as an administrator, so you are not authorised to do this operation!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }

    //only the administrator logged in to the application have acces to the endpoint below
    @GetMapping(path = "/findRecentlyPurchasedTrips")
    public ResponseEntity findRecentlyPurchasedTrips(@RequestParam Date referenceDate, @RequestParam String adminUser) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            System.out.println("User " + adminUser + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            List<TripDTO> tripDTOList = tripService.findRecentlyPurchasedTrips(referenceDate);
            if (tripDTOList.isEmpty()) {
                System.out.println("There are no trips in the reference range.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no trips in the reference range.");
            }
            return ResponseEntity.ok(tripDTOList);
        } else {
            System.out.println("You are not logged in as an administrator, so you are not authorised to do this operation!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }
}
