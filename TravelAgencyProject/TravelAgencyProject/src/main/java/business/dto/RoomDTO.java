package business.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RoomDTO {

    @NotNull(message = "The roomType field cannot be null!")
    @NotEmpty(message = "The roomType field cannot be empty!")
    @NotBlank(message = "The roomType field cannot be blank!")
    private String roomType;

    @NotNull
    private int availableRooms;

    @NotNull
    private boolean extraBed;

    public RoomDTO(String roomType, int availableRooms, boolean extraBed) {
        this.roomType = roomType;
        this.availableRooms = availableRooms;
        this.extraBed = extraBed;
    }

    public RoomDTO() {

    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(int availableRooms) {
        this.availableRooms = availableRooms;
    }

    public boolean isExtraBed() {
        return extraBed;
    }

    public void setExtraBed(boolean extraBed) {
        this.extraBed = extraBed;
    }

    public String toString() {
        return "RoomDTO: " + roomType + " , " + availableRooms + " , " + extraBed;
    }
}
