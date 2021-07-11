package frontend.controller;

import business.dto.ClientDTO;
import business.service.AccountService;
import business.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
public class ClientController {

    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;

    @PostMapping(path = "/registerClient")
    public ResponseEntity insertClient(@RequestBody @Valid ClientDTO clientDTO) {
        String cryptedPassword = accountService.cryptPassword(clientDTO.getAccountDTO().getPassword());
        clientDTO.getAccountDTO().setPassword(cryptedPassword);
        if (clientService.findClientByEmail(clientDTO.getEmail()) != null) {
            System.out.println("The client with email " + clientDTO.getEmail() + " already exists in the database");
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("The client with email " +
                    clientDTO.getEmail() + " already exists in the database.");
        } else if (clientService.findClientByUsername(clientDTO.getAccountDTO().getUsername()) != null) {
            System.out.println("The client with username " + clientDTO.getAccountDTO().getUsername() +
                    " already exists in the database.");
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("The client with username " +
                    clientDTO.getAccountDTO().getUsername() + " already exists in the database.");
        }
        clientService.insert(clientDTO);
        System.out.println(clientDTO.getSurname() + " " + clientDTO.getFirstName() + " was registered in the database.");
        return ResponseEntity.ok(clientDTO.getSurname() + " " + clientDTO.getFirstName() +
                " was registered in the database.");
    }

    @PutMapping(path = "/logOut")
    public ResponseEntity logOut(@RequestParam String username) {
        ClientDTO clientDTO = clientService.findClientByUsername(username);
        if (clientDTO == null) {
            System.out.println("Username " + username + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username " + username + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isLoggedIn()) {
            accountService.updateLogInUser(false, username);
            System.out.println("User " + username + " successfully logged out!");
            return ResponseEntity.ok("User " + username + " successfully logged out!");
        } else {
            System.out.println("User " + username + " is not logged in, so you are not authorised to do this operation!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User " + username + " is not logged in, " +
                    "so you are not authorised to do this operation!");
        }
    }

    @PutMapping(path = "/logIn")
    public ResponseEntity logIn(@RequestParam String username, @RequestParam String password) {
        ClientDTO clientDTO = clientService.findClientByUsername(username);
        if (clientDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + username + " does not exist in the database.");
        }
        String cryptedPassword = accountService.cryptPassword(password);
        if (clientService.findClientByUsernameAndPassword(username, cryptedPassword) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password entered incorrectly!");
        } else if (clientDTO.getAccountDTO().isLoggedIn()){
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(username + " already logged in!");
        } else {
            accountService.updateLogInUser(true, username);
            return ResponseEntity.ok("User " + username + " logged in successfully!");
        }
    }

    //only the administrator have access to the endpoint below when he is logged into the application
    @GetMapping(path = "/findClientByUsernameAndPassword")
    public ResponseEntity findClientByUsernameAndPassword(@RequestParam String username, @RequestParam String password,
                                                          @RequestParam String adminUser) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            System.out.println("Username " + adminUser + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username " + adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            String cryptedPassword = accountService.cryptPassword(password);
            ClientDTO clientDTOFound = clientService.findClientByUsernameAndPassword(username, cryptedPassword);
            if (clientDTOFound == null) {
                System.out.println("Does not exist the client with username " + username + " and password " +
                        password + " in the database.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Does not exist the client with username " +
                        username + " and password " + password + " in the data base.");
            } else {
                System.out.println("The searched client is displayed.");
                return ResponseEntity.ok(clientDTOFound);
            }
        } else {
            System.out.println("User " + username + " is not logged in, so you are not authorised to do this operation!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User " + username + " is not logged in, " +
                    "so you are not authorised to do this operation!");
        }
    }

    //only the administrator have access to the endpoint below when he is logged into the application
    @GetMapping(path = "/findClientByEmail")
    public ResponseEntity findClientByEmail(@RequestParam String clientEmail, @RequestParam String adminUsername) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUsername);
        if (clientDTO == null) {
            System.out.println("Username " + adminUsername + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username " + adminUsername +
                    " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            ClientDTO clientDTOFound = clientService.findClientByEmail(clientEmail);
            if (clientDTOFound == null) {
                System.out.println("The client with email " + clientEmail + " does not exist in the database.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The client with email " + clientEmail +
                        " does not exist in the database.");
            }
            System.out.println("The client with email " + clientEmail + " does not exist in the database.");
            return ResponseEntity.ok(clientDTOFound);
        } else {
            System.out.println("You are not logged in as an administrator, so you don't have access!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorized to do this operation!");
        }
    }

    // only the administrator have acces to the endpoint below when he is logged into the application
    @DeleteMapping(path = "/deleteClientByUsername")
    public ResponseEntity deleteClientByUsername(@RequestParam String usernameForDelete, @RequestParam String adminUsername) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUsername);
        if (clientDTO == null) {
            System.out.println("Username " + adminUsername + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username " + adminUsername + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            int numberOfClientsDeleted = clientService.deleteClientByUsername(usernameForDelete);
            if (numberOfClientsDeleted == 0) {
                System.out.println("Username " + usernameForDelete + " does not exist in the database.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username " + usernameForDelete + " does not exist in the database.");
            } else {
                System.out.println("The client with username " + usernameForDelete + " has been deleted ");
                return ResponseEntity.ok("The client with username " + usernameForDelete + " has been deleted ");
            }
        } else {
            System.out.println("You are not logged in as an administrator, so you don't have access!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }

    // only the administrator have acces to the endpoint below when he is logged into the application
    @GetMapping(path = "/findClientByUsername")
    public ResponseEntity findClientByUsername(@RequestParam String clientUsername, @RequestParam String adminUsername) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUsername);
        if (clientDTO == null) {
            System.out.println("Username " + adminUsername + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username " + adminUsername + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            ClientDTO clientDTOFound = clientService.findClientByUsername(clientUsername);
            if (clientDTOFound == null) {
                System.out.println("Username " + clientUsername + " does not exist in the database.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username " + clientUsername + " does not exist in the database.");
            } else {
                System.out.println("The client with username " + clientUsername + " is displayed.");
                return ResponseEntity.ok(clientDTOFound);
            }
        } else {
            System.out.println("You are not logged in as an administrator, so you don't have access!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }

    // only the administrator have access to the endpoint below when he is logged into the application
    @GetMapping(path = "/findClientByYearOfBirth")
    public ResponseEntity findClientByYearOfBirth(@RequestParam int yearOfBirth, @RequestParam String adminUsername) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUsername);
        if (clientDTO == null) {
            System.out.println("Username " + adminUsername + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username " + adminUsername + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            List<ClientDTO> clientDTOList = clientService.findClientsByYearOfBirth(yearOfBirth);
            if (clientDTOList.isEmpty()) {
                System.out.println("There is no client born in " + yearOfBirth);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no client born in " + yearOfBirth);
            } else {
                System.out.println("Clients born in " + yearOfBirth + " are displayed.");
                return ResponseEntity.ok(clientDTOList);
            }
        } else {
            System.out.println("You are not logged in as an administrator, so you don't have access!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }

    // only the administrator have acces to the endpoint below when he is logged into the application
    @GetMapping(path = "/findClientsBySurnameAndFirstName")
    public ResponseEntity findClientsBySurnameAndFirstName(@RequestParam String surname, @RequestParam String firstName,
                                                           @RequestParam String adminUser) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            System.out.println(adminUser + " does not exist in the data base.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            List<ClientDTO> clientDTOList = clientService.findClientsBySurnameAndFirstName(surname, firstName);
            if (clientDTOList.isEmpty()) {
                System.out.println("The client with surname " + surname + " and firstName " + firstName + " does not exist in the database.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The client with surname " + surname +
                        " and firstName " + firstName + " does not exist in the database.");
            } else {
                System.out.println("The clients are displayed.");
                return ResponseEntity.ok(clientDTOList);
            }
        } else {
            System.out.println("You are not logged in as an administrator, so you don't have access!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }
}
