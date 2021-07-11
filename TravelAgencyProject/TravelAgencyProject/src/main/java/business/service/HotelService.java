package business.service;

import business.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.dao.CityDAO;
import persistence.dao.ContinentDAO;
import persistence.dao.CountryDAO;
import persistence.dao.HotelDAO;
import persistence.entities.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class HotelService {

    @Autowired
    HotelDAO hotelDAO;
    @Autowired
    CityDAO cityDAO;
    @Autowired
    CountryDAO countryDAO;
    @Autowired
    ContinentDAO continentDAO;

    public void insert(HotelDTO hotelDTO) {
        Hotel hotel = new Hotel();
        hotel.setName(hotelDTO.getName());
        hotel.setStars(hotelDTO.getStars());
        hotel.setDescription(hotelDTO.getDescription());
        setCity(hotelDTO, hotel);
        Set<Room> roomSet = new HashSet<>();
        for (RoomDTO roomDTO : hotelDTO.getRoomDTOSet()) {
            Room room = new Room();
            room.setRoomType(roomDTO.getRoomType());
            room.setAvailableRooms(roomDTO.getAvailableRooms());
            room.setExtraBed(roomDTO.isExtraBed());
            room.setHotel(hotel);
            roomSet.add(room);
        }
        hotel.setRoomSet(roomSet);
        hotelDAO.insert(hotel);
    }

    //here I set city to use in insert method above
    public void setCity(HotelDTO hotelDTO, Hotel hotel) {
        City cityFound = cityDAO.findCityByName(hotelDTO.getCityDTO().getName());
        if (cityFound != null) {
            hotel.setCity(cityFound);
        } else {
            City city = new City();
            city.setName(hotelDTO.getCityDTO().getName());
            setCountry(hotelDTO, city);
            hotel.setCity(city);
        }
    }

    //here I set country to use in setCity method above
    public void setCountry(HotelDTO hotelDTO, City city) {
        Country countryFound = countryDAO.findCountryByName(hotelDTO.getCityDTO().getCountryDTO().getName());
        if (countryFound != null) {
            city.setCountry(countryFound);
        } else {
            Country country = new Country();
            country.setName(hotelDTO.getCityDTO().getCountryDTO().getName());
            setContinent(hotelDTO, country);
            city.setCountry(country);
        }
    }

    //here I set continent to use in setCountry method above
    public void setContinent(HotelDTO hotelDTO, Country country) {
        Continent continentFound = continentDAO.findContinentByName(hotelDTO.getCityDTO().getCountryDTO().getContinentDTO().getName());
        if (continentFound != null) {
            country.setContinent(continentFound);
        } else {
            Continent continent = new Continent();
            continent.setName(hotelDTO.getCityDTO().getCountryDTO().getContinentDTO().getName());
            country.setContinent(continent);
        }
    }

    public Long countHotelsByNameAndByCityName(String hotelName, String cityName) {
        return hotelDAO.countHotelsByNameAndByCityName(hotelName, cityName);
    }

    public List<HotelDTO> findAllHotelsByCityName(String cityDTOName) {
        List<Hotel> hotelList = hotelDAO.findAllHotelsByCity(cityDTOName);
        List<HotelDTO> hotelDTOList = new LinkedList<>();
        for (Hotel hotel : hotelList) {
            HotelDTO hotelDTO = new HotelDTO();
            hotelDTO.setName(hotel.getName());
            hotelDTO.setStars(hotel.getStars());
            hotelDTO.setDescription(hotel.getDescription());
            CityDTO cityDTO = new CityDTO();
            cityDTO.setName(hotel.getCity().getName());
            CountryDTO countryDTO = new CountryDTO();
            countryDTO.setName(hotel.getCity().getCountry().getName());
            ContinentDTO continentDTO = new ContinentDTO();
            continentDTO.setName(hotel.getCity().getCountry().getContinent().getName());
            countryDTO.setContinentDTO(continentDTO);
            cityDTO.setCountryDTO(countryDTO);
            hotelDTO.setCityDTO(cityDTO);
            setRoomDTOSet(hotel, hotelDTO);
            hotelDTOList.add(hotelDTO);
        }
        return hotelDTOList;
    }

    //here I set roomDTOSet to use in findAllHotelsDTOByCityDTOName method and findHotelByNameAndByCityName method
    public void setRoomDTOSet(Hotel hotel, HotelDTO hotelDTO) {
        Set<RoomDTO> roomDTOSet = new HashSet<>();
        for (Room room : hotel.getRoomSet()) {
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setRoomType(room.getRoomType());
            roomDTO.setAvailableRooms(room.getAvailableRooms());
            roomDTO.setExtraBed(room.isExtraBed());
            roomDTOSet.add(roomDTO);
        }
        hotelDTO.setRoomDTOSet(roomDTOSet);
    }

    public HotelDTO findHotelByNameAndByCityName(String hotelDTOName, String cityDTOName) {
        Hotel hotelFound = hotelDAO.findHotelByNameAndByCityName(hotelDTOName, cityDTOName);
        if (hotelFound == null) {
            return null;
        } else {
            HotelDTO hotelDTO = new HotelDTO();
            hotelDTO.setName(hotelFound.getName());
            hotelDTO.setStars(hotelFound.getStars());
            hotelDTO.setDescription(hotelFound.getDescription());
            CityDTO cityDTO = new CityDTO();
            cityDTO.setName(hotelFound.getCity().getName());
            CountryDTO countryDTO = new CountryDTO();
            countryDTO.setName(hotelFound.getCity().getCountry().getName());
            ContinentDTO continentDTO = new ContinentDTO();
            continentDTO.setName(hotelFound.getCity().getCountry().getContinent().getName());
            countryDTO.setContinentDTO(continentDTO);
            cityDTO.setCountryDTO(countryDTO);
            hotelDTO.setCityDTO(cityDTO);
            setRoomDTOSet(hotelFound, hotelDTO);
            return hotelDTO;
        }
    }

    public List<HotelDTO> findHotelsByStarsInCity(int stars, CityDTO cityDTO) {
        List<Hotel> hotelList = hotelDAO.findHotelsByStarsInCity(stars, cityDTO);
        List<HotelDTO> hotelDTOList = new LinkedList<>();
        for (Hotel hotel : hotelList) {
            HotelDTO hotelDTO = new HotelDTO();
            hotelDTO.setName(hotel.getName());
            hotelDTO.setStars(hotel.getStars());
            hotelDTO.setDescription(hotel.getDescription());
            hotelDTO.setCityDTO(cityDTO);
            setRoomDTOSet(hotel, hotelDTO);
            hotelDTOList.add(hotelDTO);
        }
        return hotelDTOList;
    }

    public Integer updateHotelName(String currentName, String newName) {
        return hotelDAO.updateHotelName(currentName, newName);
    }

    public Integer deleteHotelByName(String name) {
        return hotelDAO.deleteHotelByName(name);
    }

}
