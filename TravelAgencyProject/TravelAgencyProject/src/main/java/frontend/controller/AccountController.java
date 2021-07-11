package frontend.controller;

import business.dto.ClientDTO;
import business.service.AccountService;
import business.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;
    @Autowired
    ClientService clientService;

    //only the user who is logged in to the application has access to the endpoint below
    @DeleteMapping(path = "/deleteAccountByUsername")
    public ResponseEntity deleteAccountByUsername(@RequestParam String usernameForDelete, @RequestParam String adminUsername) {
        ClientDTO clientDTOByUsername = clientService.findClientByUsername(adminUsername);
        if (clientDTOByUsername == null) {
            System.out.println("Username " + adminUsername + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username " + adminUsername + " does not exist in the database.");
        }
        if (clientDTOByUsername.getAccountDTO().isAdminRole() && clientDTOByUsername.getAccountDTO().isLoggedIn()) {
            int numberOfAccountsDeleted = accountService.deleteAccountByUsername(usernameForDelete);
            if (numberOfAccountsDeleted == 0) {
                System.out.println(usernameForDelete + " does not exist in the database.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(usernameForDelete + " does not exist in the database.");
            } else {
                System.out.println("Username " + usernameForDelete + " has been deleted.");
                return ResponseEntity.ok("Username " + usernameForDelete + " has been deleted.");
            }
        } else {
            System.out.println("You are not logged in as an administrator, so you don't have acces!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }

    //the user who is logged in to the application can change his username
    @PutMapping(path = "/changeUsername")
    public ResponseEntity changeUsername(@RequestParam String newUsername, @RequestParam String currentUsername) {
        ClientDTO clientDTO = clientService.findClientByUsername(currentUsername);
        if (clientDTO == null) {
            System.out.println("Username " + currentUsername + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username " + currentUsername + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isLoggedIn()) {
            accountService.changeUsername(newUsername, currentUsername);
            System.out.println("User " + currentUsername + " has been changed in " + newUsername + ".");
            return ResponseEntity.ok("User " + currentUsername + " has been changed in " + newUsername + ".");
        } else {
            System.out.println("User " + currentUsername + " must be logged in!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User " + currentUsername + " must be logged in!");
        }
    }

}

