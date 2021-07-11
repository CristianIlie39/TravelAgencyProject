package frontend.controller;

import business.dto.ClientDTO;
import business.dto.ContinentDTO;
import business.service.ClientService;
import business.service.ContinentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ContinentController {

    @Autowired
    ContinentService continentService;
    @Autowired
    ClientService clientService;

    @PostMapping(path = "/insertContinent")
    public ResponseEntity insert(@RequestBody @Valid ContinentDTO continentDTO, @RequestParam String adminUser) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            System.out.println("User " + adminUser + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            if (continentService.countContinentsByName(continentDTO.getName()) != 0) {
                System.out.println("The continent " + continentDTO.getName() + " already exists in database.");
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("The continent " + continentDTO.getName() + " already exists in database.");
            }
            continentService.insert(continentDTO);
            System.out.println("The continent " + continentDTO.getName() + " has been added to the database.");
            return ResponseEntity.ok("The continent " + continentDTO.getName() + " was added to the database.");
        } else {
            System.out.println("You are not logged in as an administrator, so you are not authorised to do this operation!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }

    }

    @GetMapping(path = "/findContinent")
    public ResponseEntity findContinent(@RequestParam String name) {
        ContinentDTO continentFound = continentService.findContinentDTOByName(name);
        if (continentFound == null) {
            System.out.println("The continent " + name + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The continent " + name + " does not exist in the database..");
        }
        System.out.println(continentFound.toString());
        return ResponseEntity.ok(continentFound);
    }

    @GetMapping(path = "/findAllContinents")
    public ResponseEntity findAll() {
        List<ContinentDTO> continentDTOList = continentService.findAllContinents();
        if (continentDTOList.isEmpty()) {
            System.out.println("There are no continents in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no continents in the database.");
        }
        System.out.println("The continents are displayed.");
        return ResponseEntity.ok(continentDTOList);
    }

    @DeleteMapping(path = "/deleteContinentByName")
    public ResponseEntity deleteContinentByName(@RequestParam String name, @RequestParam String adminUser) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            System.out.println("User " + adminUser + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            Integer numberOfContinentsDeleted = continentService.deleteContinentByName(name);
            if (numberOfContinentsDeleted > 0) {
                System.out.println("The continent " + name + " has been deleted from the database.");
                return ResponseEntity.ok("The continent " + name + " has been deleted from the database.");
            } else {
                System.out.println("The continent " + name + " does not exist in the database.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The continent does not exist in the database.");
            }
        } else {
            System.out.println("You are not logged in as an administrator, so you are not authorised to do this operation!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }

    @PutMapping(path = "/updateContinentName")
    public ResponseEntity updateContinentName(@RequestParam String currentName, @RequestParam String newName,
                                              @RequestParam String adminUser) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            System.out.println("User " + adminUser + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            if (continentService.countContinentsByName(newName) != 0) {
                System.out.println(newName + " already exists in the database.");
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(newName + " already exists in the database.");
            } else {
                Integer numberOfContinentsUpdated = continentService.updateContinentName(currentName, newName);
                if (numberOfContinentsUpdated > 0) {
                    System.out.println(currentName + " was changed in " + newName);
                    return ResponseEntity.ok(currentName + " was changed in " + newName);
                } else {
                    System.out.println("The continent " + currentName + " does not exist in the database.");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The continent " + currentName + " does not exist in the database.");
                }
            }
        } else {
            System.out.println("You are not logged in as an administrator, so you are not authorised to do this operation!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }
}
