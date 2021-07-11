package persistence.entities;

import javax.persistence.*;
import java.util.Objects;

@NamedQueries({

        @NamedQuery(name = "findAvailableRoomByTypeAndByExtraBedAndByHotel", query = "SELECT room FROM Room room WHERE " +
                "room.roomType = :roomType AND room.extraBed = :extraBed AND room.hotel.name = :name"),
        @NamedQuery(name = "findRoomByTypeAndByHotel", query = "SELECT room FROM Room room WHERE " +
                "room.roomType = :roomType AND room.hotel.name = :hotelName"),
        @NamedQuery(name = "findRoomByExtraBedAndByHotel", query = "SELECT room FROM Room room WHERE " +
                "room.extraBed = :extraBed AND room.hotel.name = :hotelName"),
        @NamedQuery(name = "findAllRoomsByHotel", query = "SELECT room FROM Room room WHERE room.hotel.name = :hotelName"),
        @NamedQuery(name = "updateRoomByTypeAndByHotel", query = "UPDATE Room room SET " +
                "room.availableRooms = :availableRooms WHERE room.roomType = :roomType AND " +
                "room.hotel IN (SELECT hotel FROM Hotel hotel WHERE name = :hotelName)"),
        @NamedQuery(name = "updateAvailableRooms", query = "UPDATE Room room SET " +
                "room.availableRooms = availableRooms - :roomNumber WHERE room.roomType = :roomType AND " +
                "room.hotel IN (SELECT hotel FROM Hotel hotel WHERE name = :hotelName)"),
        @NamedQuery(name = "deleteRoom", query = "DELETE Room room WHERE room.roomType = :roomType AND " +
                "room.hotel IN (SELECT hotel FROM Hotel hotel WHERE hotel.name = :hotelName)")
})

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "room_type")
    private String roomType;

    @Column(name = "available_rooms")
    private int availableRooms;

    @Column(name = "extra_bed")
    private boolean extraBed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotels_id")
    private Hotel hotel;

    public Room(String roomType, int availableRooms, boolean extraBed) {
        this.roomType = roomType;
        this.availableRooms = availableRooms;
        this.extraBed = extraBed;
    }

    public Room() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String toString() {
        return "Room: " + roomType + " , " + availableRooms + " , " + extraBed + " , " + hotel.getName();
    }

    public boolean equals(Object object) {
        if (object instanceof Room) {
            if (this.id == ((Room) object).getId() && this.roomType.equals(((Room) object).getRoomType()) &&
                    this.availableRooms == (((Room) object).getAvailableRooms()) &&
                    this.extraBed == ((Room) object).isExtraBed()) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(id, roomType, availableRooms, extraBed);
    }
}
