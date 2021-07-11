package business.service;

import business.dto.RoomDTO;
import business.dto.TripDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.dao.RoomDAO;
import persistence.entities.Room;

import java.util.LinkedList;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    RoomDAO roomDAO;

    public RoomDTO findAvailableRoomByTypeAndByExtrabedAndByHotel(String roomType, boolean extraBed, String name) {
        Room room = roomDAO.findAvailableRoomByTypeAndByExtrabedAndByHotel(roomType, extraBed, name);
        if (room == null) {
            return null;
        }
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setAvailableRooms(room.getAvailableRooms());
        roomDTO.setRoomType(room.getRoomType());
        roomDTO.setExtraBed(room.isExtraBed());
        return roomDTO;
    }

    public RoomDTO findRoomByTypeAndByHotel(String roomType, String hotelName) {
        Room room = roomDAO.findRoomByTypeAndByHotel(roomType, hotelName);
        if (room == null) {
            return null;
        }
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoomType(room.getRoomType());
        roomDTO.setAvailableRooms(room.getAvailableRooms());
        roomDTO.setExtraBed(room.isExtraBed());
        return roomDTO;
    }

    public List<RoomDTO> findRoomByExtraBedAndByHotel(boolean extraBed, String hotelName) {
        List<Room> roomList = roomDAO.findRoomByExtraBedAndByHotel(extraBed, hotelName);
        List<RoomDTO> roomDTOList = new LinkedList<>();
        for (Room room : roomList) {
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setExtraBed(room.isExtraBed());
            roomDTO.setRoomType(room.getRoomType());
            roomDTO.setAvailableRooms(room.getAvailableRooms());
            roomDTOList.add(roomDTO);
        }
        return roomDTOList;
    }

    public List<RoomDTO> findAllRoomsByHotel(String hotelName) {
        List<Room> roomList = roomDAO.findAllRoomsByHotel(hotelName);
        List<RoomDTO> roomDTOList = new LinkedList<>();
        for (Room room : roomList) {
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setAvailableRooms(room.getAvailableRooms());
            roomDTO.setRoomType(room.getRoomType());
            roomDTO.setExtraBed(room.isExtraBed());
            roomDTOList.add(roomDTO);
        }
        return roomDTOList;
    }

    public int updateRoomByTypeAndByHotel(int availableRooms, String roomType, String hotelName) {
        return roomDAO.updateRoomByTypeAndByHotel(availableRooms, roomType, hotelName);
    }

    public boolean checkSingleRoomAvailability(TripDTO tripDTO, int singleRooms) {
        Room singleRoom = roomDAO.findRoomByTypeAndByHotel("single", tripDTO.getHotelDTO().getName());
        return singleRoom.getAvailableRooms() >= singleRooms;
    }

    public boolean checkDoubleRoomAvailability(TripDTO tripDTO, int doubleRooms) {
        Room doubleRoom = roomDAO.findRoomByTypeAndByHotel("double", tripDTO.getHotelDTO().getName());
        return doubleRoom.getAvailableRooms() >= doubleRooms;
    }

    public boolean checkFamilyRoomAvailability(TripDTO tripDTO, int familyRooms) {
        Room familyRoom = roomDAO.findRoomByTypeAndByHotel("family", tripDTO.getHotelDTO().getName());
        return familyRoom.getAvailableRooms() >= familyRooms;
    }

    public boolean checkApartmentAvailability(TripDTO tripDTO, int apartments) {
        Room apartment = roomDAO.findRoomByTypeAndByHotel("apartment", tripDTO.getHotelDTO().getName());
        return apartment.getAvailableRooms() >= apartments;
    }

    //I created the method for updating the available rooms depending on the type of room, from a certain hotel
    public void updateAvailableRooms(int roomNumber, String roomType, String hotelName) {
        roomDAO.updateAvailableRooms(roomNumber, roomType, hotelName);
    }

    public int deleteRoom(String roomType, String hotelName) {
        return roomDAO.deleteRoomByTypeAndByHotel(roomType, hotelName);
    }

}
