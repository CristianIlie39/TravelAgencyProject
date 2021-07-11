package frontend.controller;

import business.dto.CityDTO;
import business.dto.ClientDTO;
import business.dto.HotelDTO;
import business.service.CityService;
import business.service.ClientService;
import business.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class HotelController {

    @Autowired
    HotelService hotelService;
    @Autowired
    CityService cityService;
    @Autowired
    ClientService clientService;

    @PostMapping(path = "/insertHotel")
    public ResponseEntity insert(@RequestBody @Valid HotelDTO hotelDTO, @RequestParam String adminUser) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            System.out.println("User " + adminUser + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            if (hotelService.countHotelsByNameAndByCityName(hotelDTO.getName(), hotelDTO.getCityDTO().getName()) != 0) {
                System.out.println(hotelDTO.getName() + " already exists in the database.");
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hotelDTO.getName() + " already exists in the database.");
            }
            hotelService.insert(hotelDTO);
            System.out.println(hotelDTO.getName() + " has been added to the database.");
            return ResponseEntity.ok(hotelDTO.getName() + " has been added to the database.");
        } else {
            System.out.println("You are not logged in as an administrator, so you are not authorised to do this operation!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }

    @GetMapping(path = "/findAllHotelsByCity")
    public ResponseEntity findAllHotelsByCity(@RequestParam String cityName) {
        if (cityService.countCitiesByName(cityName) == 0) {
            System.out.println(cityName + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cityName + " does not exist in the database.");
        }
        List<HotelDTO> hotelDTOList = hotelService.findAllHotelsByCityName(cityName);
        if (hotelDTOList.isEmpty()) {
            System.out.println("No hotels found for " + cityName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hotels found for " + cityName);
        } else {
            System.out.println("Hotels from " + cityName + " are displyed.");
            return ResponseEntity.ok(hotelDTOList);
        }
    }

    @GetMapping(path = "/findHotelByNameAndByCityName")
    public ResponseEntity findHotelByNameAndByCityName(@RequestParam String hotelName, @RequestParam String cityName) {
        HotelDTO hotelDTO = hotelService.findHotelByNameAndByCityName(hotelName, cityName);
        if (hotelDTO == null) {
            System.out.println(hotelName + " from " + cityName + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hotelName + " from " + cityName + " does not exist in the database.");
        } else {
            System.out.println(hotelName + " from " + cityName + " was displayed.");
            return ResponseEntity.ok(hotelDTO);
        }
    }

    @GetMapping(path = "/findHotelsByStars")
    public ResponseEntity findHotelsByStarsInCity(@RequestParam int stars, @RequestBody CityDTO cityDTO) {
        if (cityService.countCitiesByName(cityDTO.getName()) == 0) {
            System.out.println(cityDTO.getName() + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cityDTO.getName() + " does not exist in the database.");
        }
        List<HotelDTO> hotelDTOList = hotelService.findHotelsByStarsInCity(stars, cityDTO);
        if (hotelDTOList.isEmpty()) {
            System.out.println("No hotel found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hotel found.");
        } else {
            System.out.println(stars + " star hotels in " + cityDTO.getName() + " are displayed.");
            return ResponseEntity.ok(hotelDTOList);
        }
    }

    @PutMapping(path = "/updateHotelName")
    public ResponseEntity updateHotelName(@RequestParam String currentName, @RequestParam String newName, @RequestParam String adminUser) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            System.out.println("User " + adminUser + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            Integer numberOfHotelsUpdated = hotelService.updateHotelName(currentName, newName);
            if (numberOfHotelsUpdated == 0) {
                System.out.println(currentName + " does not exist in the database.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(currentName + " does not exist in the database.");
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

    @DeleteMapping(path = "/deleteHotel")
    public ResponseEntity deleteHotelByName(@RequestParam String name, @RequestParam String adminUser) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            System.out.println("User " + adminUser + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            Integer numberOfHotelsDeleted = hotelService.deleteHotelByName(name);
            if (numberOfHotelsDeleted == 0) {
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
}
