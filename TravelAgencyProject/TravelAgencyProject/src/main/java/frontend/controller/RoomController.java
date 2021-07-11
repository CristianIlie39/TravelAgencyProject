package frontend.controller;

import business.dto.ClientDTO;
import business.dto.RoomDTO;
import business.service.ClientService;
import business.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class RoomController {

    @Autowired
    RoomService roomService;
    @Autowired
    ClientService clientService;

    @GetMapping(path = "/findAvailableRoomByTypeAndByExtrabedAndByHotel")
    public ResponseEntity findRoom(@RequestParam String roomType, @RequestParam boolean extraBed,
                                   @RequestParam String hotelName) {
        RoomDTO roomDTO = roomService.findAvailableRoomByTypeAndByExtrabedAndByHotel(roomType, extraBed, hotelName);
        if (roomDTO == null) {
            System.out.println("No room found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No room found.");
        } else {
            System.out.println("The room has been found.");
            return ResponseEntity.ok(roomDTO);
        }
    }

    @GetMapping(path = "/findRoomByTypeAndByHotel")
    public ResponseEntity findRoomByTypeAndByHotel(@RequestParam String roomType,
                                                   @RequestParam String hotelName) {
        RoomDTO roomDTO = roomService.findRoomByTypeAndByHotel(roomType, hotelName);
        if (roomDTO == null) {
            System.out.println("No room found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No room found.");
        } else {
            System.out.println("The room has been found.");
            return ResponseEntity.ok(roomDTO);
        }
    }

    @GetMapping(path = "/findRoomByExtraBedAndByHotel")
    public ResponseEntity findRoomByExtraBedAndByHotel(@RequestParam boolean extraBed,
                                                       @RequestParam String hotelName) {
        List<RoomDTO> roomDTOList = roomService.findRoomByExtraBedAndByHotel(extraBed, hotelName);
        if (roomDTOList.isEmpty()) {
            System.out.println("No room found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No room found");
        } else {
            System.out.println("The room has been found");
            return ResponseEntity.ok(roomDTOList);
        }
    }

    @GetMapping(path = "/findAllRoomsByHotel")
    public ResponseEntity findAllRoomsByHotel(@RequestParam String hotelName) {
        List<RoomDTO> roomDTOList = roomService.findAllRoomsByHotel(hotelName);
        if (roomDTOList.isEmpty()) {
            System.out.println("No room found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No room found.");
        } else {
            System.out.println("The rooms were found.");
            return ResponseEntity.ok(roomDTOList);
        }
    }

    @PutMapping(path = "/updateAvailableRoomsByTypeAndByHotel")
    public ResponseEntity updateAvailableRooms(@RequestParam int availableRooms, @RequestParam String roomType,
                                     @RequestParam String hotelName, @RequestParam String adminUser) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            System.out.println("User " + adminUser + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            int numberOfRoomsUpdated = roomService.updateRoomByTypeAndByHotel(availableRooms, roomType, hotelName);
            if (numberOfRoomsUpdated == 0) {
                System.out.println("No room updated.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No room updated.");
            } else {
                System.out.println("The rooms have been updated.");
                return ResponseEntity.ok("The room has been updated.");
            }
        } else {
            System.out.println("You are not logged in as an administrator, so you are not authorised to do this operation!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }

    @DeleteMapping(path = "/deleteRoom")
    public ResponseEntity deleteRoom(@RequestParam String roomType, @RequestParam String hotelName, @RequestParam String adminUser) {
        ClientDTO clientDTO = clientService.findClientByUsername(adminUser);
        if (clientDTO == null) {
            System.out.println("User " + adminUser + " does not exist in the database.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User " + adminUser + " does not exist in the database.");
        }
        if (clientDTO.getAccountDTO().isAdminRole() && clientDTO.getAccountDTO().isLoggedIn()) {
            int numberOfRoomsDeleted = roomService.deleteRoom(roomType, hotelName);
            if (numberOfRoomsDeleted == 0) {
                System.out.println("The " + roomType + " type does not exist in the " + hotelName);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The " + roomType + " type does not exist in the " + hotelName);
            } else {
                System.out.println("The room has been deleted from the database");
                return ResponseEntity.ok("The room has been deleted from the database.");
            }
        } else {
            System.out.println("You are not logged in as an administrator, so you are not authorised to do this operation!");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not logged in as an administrator, " +
                    "so you are not authorised to do this operation!");
        }
    }
}
