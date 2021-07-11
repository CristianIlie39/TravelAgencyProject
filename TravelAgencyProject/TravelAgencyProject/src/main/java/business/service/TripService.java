package business.service;

import business.dto.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.config.HibernateUtil;
import persistence.dao.*;
import persistence.entities.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;

@Service
public class TripService {

    @Autowired
    TripDAO tripDAO;
    @Autowired
    AirportDAO airportDAO;
    @Autowired
    HotelDAO hotelDAO;
    @Autowired
    CityDAO cityDAO;
    @Autowired
    CountryDAO countryDAO;
    @Autowired
    ContinentDAO continentDAO;

    public void insert(TripDTO tripDTO) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Trip trip = getTrip(tripDTO, session);
        tripDAO.insert(trip, session);
        session.getTransaction().commit();
        session.close();
    }

    public Trip getTrip(TripDTO tripDTO, Session session) {
        Trip trip = new Trip();
        trip.setName(tripDTO.getName());
        setAirport(tripDTO, trip, session);
        setHotel(tripDTO, trip, session);
        trip.setDepartureDate(tripDTO.getDepartureDate());
        trip.setReturnDate(tripDTO.getReturnDate());
        trip.setNumberDays(tripDTO.getNumberDays());
        trip.setMealType(tripDTO.getMealType());
        trip.setAdultPrice(tripDTO.getAdultPrice());
        trip.setKidPrice(tripDTO.getKidPrice());
        trip.setPromoted(tripDTO.isPromoted());
        trip.setStock(tripDTO.getStock());
        return trip;
    }

    //here I set Airport
    public void setAirport(TripDTO tripDTO, Trip trip, Session session) {
        Airport airportFound = airportDAO.findAirportByName(tripDTO.getAirportDTO().getName(), session);
        if (airportFound != null) {
            trip.setAirport(airportFound);
        } else {
            Airport airport = new Airport();
            airport.setName(tripDTO.getAirportDTO().getName());
            setCity(tripDTO, airport, session);
            setFlightSet(tripDTO, airport);
            trip.setAirport(airport);
        }
    }

    public void setCity(TripDTO tripDTO, Airport airport, Session session) {
        City cityFound = cityDAO.findCityByName(tripDTO.getAirportDTO().getCityDTO().getName(), session);
        if (cityFound != null) {
            airport.setCity(cityFound);
        } else {
            City city = new City();
            city.setName(tripDTO.getAirportDTO().getCityDTO().getName());
            setCountry(tripDTO, city, session);
            airport.setCity(city);
        }
    }

    public void setCountry(TripDTO tripDTO, City city, Session session) {
        Country countryFound = countryDAO.findCountryByName(tripDTO.getAirportDTO().getCityDTO().getCountryDTO().getName(), session);
        if (countryFound != null) {
            city.setCountry(countryFound);
        } else {
            Country country = new Country();
            country.setName(tripDTO.getAirportDTO().getCityDTO().getCountryDTO().getName());
            setContinent(tripDTO, country, session);
            city.setCountry(country);
        }
    }

    public void setContinent(TripDTO tripDTO, Country country, Session session) {
        Continent continentFound = continentDAO.findContinentByName(tripDTO.getAirportDTO().getCityDTO().getCountryDTO().getContinentDTO().getName(), session);
        if (continentFound != null) {
            country.setContinent(continentFound);
        } else {
            Continent continent = new Continent();
            continent.setName(tripDTO.getAirportDTO().getCityDTO().getCountryDTO().getContinentDTO().getName());
            country.setContinent(continent);
        }
    }

    public void setFlightSet(TripDTO tripDTO, Airport airport) {
        Set<Flight> flightSet = new HashSet<>();
        for (FlightDTO flightDTO : tripDTO.getAirportDTO().getFlightDTOSet()) {
            Flight flight = new Flight();
            flight.setFlightDepartureDate(flightDTO.getFlightDepartureDate());
            flight.setFlightDepartureTime(flightDTO.getFlightDepartureTime());
            flight.setFlightReturnDate(flightDTO.getFlightReturnDate());
            flight.setFlightReturnTime(flightDTO.getFlightReturnTime());
            flight.setFlightTo(flightDTO.getFlightTo());
            flight.setPrice(flightDTO.getPrice());
            flight.setAvailableSeats(flightDTO.getAvailableSeats());
            flight.setAirport(airport);
            flightSet.add(flight);
        }
        airport.setFlightSet(flightSet);
    }

    //here I set Hotel
    public void setHotel(TripDTO tripDTO, Trip trip, Session session) {
        Hotel hotelFound = hotelDAO.findHotelByNameAndByCityName(tripDTO.getHotelDTO().getName(), tripDTO.getHotelDTO().getCityDTO().getName(), session);
        if (hotelFound != null) {
            trip.setHotel(hotelFound);
        } else {
            Hotel hotel = new Hotel();
            hotel.setName(tripDTO.getHotelDTO().getName());
            hotel.setStars(tripDTO.getHotelDTO().getStars());
            hotel.setDescription(tripDTO.getHotelDTO().getDescription());
            setCityForHotel(tripDTO, hotel, session);
            setRoomSet(tripDTO, hotel);
            trip.setHotel(hotel);
        }
    }

    public void setCityForHotel(TripDTO tripDTO, Hotel hotel, Session session) {
        City cityFoundForHotel = cityDAO.findCityByName(tripDTO.getHotelDTO().getCityDTO().getName(), session);
        if (cityFoundForHotel != null) {
            hotel.setCity(cityFoundForHotel);
        } else {
            City city = new City();
            city.setName(tripDTO.getHotelDTO().getCityDTO().getName());
            setCountryForHotel(tripDTO, city, session);
            hotel.setCity(city);
        }
    }

    public void setCountryForHotel(TripDTO tripDTO, City city, Session session) {
        Country countryFoundForHotel = countryDAO.findCountryByName(tripDTO.getHotelDTO().getCityDTO().getCountryDTO().getName(), session);
        if (countryFoundForHotel != null) {
            city.setCountry(countryFoundForHotel);
        } else {
            Country country = new Country();
            country.setName(tripDTO.getHotelDTO().getCityDTO().getCountryDTO().getName());
            setContinentForHotel(tripDTO, country, session);
            city.setCountry(country);
        }
    }

    public void setContinentForHotel(TripDTO tripDTO, Country country, Session session) {
        Continent continentFoundForHotel = continentDAO.findContinentByName(tripDTO.getHotelDTO().getCityDTO().getCountryDTO().getContinentDTO().getName(), session);
        if (continentFoundForHotel != null) {
            country.setContinent(continentFoundForHotel);
        } else {
            Continent continent = new Continent();
            continent.setName(tripDTO.getHotelDTO().getCityDTO().getCountryDTO().getContinentDTO().getName());
            country.setContinent(continent);
        }
    }

    public void setRoomSet(TripDTO tripDTO, Hotel hotel) {
        Set<Room> roomSet = new HashSet<>();
        for (RoomDTO roomDTO : tripDTO.getHotelDTO().getRoomDTOSet()) {
            Room room = new Room();
            room.setRoomType(roomDTO.getRoomType());
            room.setExtraBed(roomDTO.isExtraBed());
            room.setAvailableRooms(roomDTO.getAvailableRooms());
            room.setHotel(hotel);
            roomSet.add(room);
        }
        hotel.setRoomSet(roomSet);
    }

    public long countTripByName(String name) {
        return tripDAO.countTripByName(name);
    }

    //here I convert Trip into TripDTO
    public TripDTO getTripDTO(Trip trip) {
        TripDTO tripDTO = new TripDTO();
        tripDTO.setName(trip.getName());
        setAirportDTO(trip, tripDTO);
        setHotelDTO(trip, tripDTO);
        tripDTO.setDepartureDate(trip.getDepartureDate());
        tripDTO.setReturnDate(trip.getReturnDate());
        tripDTO.setNumberDays(trip.getNumberDays());
        tripDTO.setMealType(trip.getMealType());
        tripDTO.setAdultPrice(trip.getAdultPrice());
        tripDTO.setKidPrice(trip.getKidPrice());
        tripDTO.setPromoted(trip.isPromoted());
        tripDTO.setStock(trip.getStock());
        return tripDTO;
    }

    public void setAirportDTO(Trip trip, TripDTO tripDTO) {
        AirportDTO airportDTO = new AirportDTO();
        airportDTO.setName(trip.getAirport().getName());
        setCityDTO(trip, airportDTO);
        setFlightDTOSet(trip, airportDTO);
        tripDTO.setAirportDTO(airportDTO);
    }

    public void setCityDTO(Trip trip, AirportDTO airportDTO) {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setName(trip.getAirport().getCity().getName());
        setCountryDTO(trip, cityDTO);
        airportDTO.setCityDTO(cityDTO);
    }

    public void setCountryDTO(Trip trip, CityDTO cityDTO) {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setName(trip.getAirport().getCity().getCountry().getName());
        setContinentDTO(trip, countryDTO);
        cityDTO.setCountryDTO(countryDTO);
    }

    public void setContinentDTO(Trip trip, CountryDTO countryDTO) {
        ContinentDTO continentDTO = new ContinentDTO();
        continentDTO.setName(trip.getAirport().getCity().getCountry().getContinent().getName());
        countryDTO.setContinentDTO(continentDTO);
    }

    public void setFlightDTOSet(Trip trip, AirportDTO airportDTO) {
        Set<FlightDTO> flightDTOSet = new HashSet<>();
        for (Flight flight : trip.getAirport().getFlightSet()) {
            FlightDTO flightDTO = new FlightDTO();
            flightDTO.setFlightDepartureDate(flight.getFlightDepartureDate());
            flightDTO.setFlightDepartureTime(flight.getFlightDepartureTime());
            flightDTO.setFlightReturnDate(flight.getFlightReturnDate());
            flightDTO.setFlightReturnTime(flight.getFlightReturnTime());
            flightDTO.setFlightTo(flight.getFlightTo());
            flightDTO.setPrice(flight.getPrice());
            flightDTO.setAvailableSeats(flight.getAvailableSeats());
            flightDTOSet.add(flightDTO);
        }
        airportDTO.setFlightDTOSet(flightDTOSet);
    }

    public void setHotelDTO(Trip trip, TripDTO tripDTO) {
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setName(trip.getHotel().getName());
        hotelDTO.setStars(trip.getHotel().getStars());
        hotelDTO.setDescription(trip.getHotel().getDescription());
        setCityDTOForHotelDTO(trip, hotelDTO);
        setRoomDTOSet(trip, hotelDTO);
        tripDTO.setHotelDTO(hotelDTO);
    }

    public void setCityDTOForHotelDTO(Trip trip, HotelDTO hotelDTO) {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setName(trip.getHotel().getCity().getName());
        setCountryDTOForHotelDTO(trip, cityDTO);
        hotelDTO.setCityDTO(cityDTO);
    }

    public void setCountryDTOForHotelDTO(Trip trip, CityDTO cityDTO) {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setName(trip.getHotel().getCity().getCountry().getName());
        setContinentDTOForHotelDTO(trip, countryDTO);
        cityDTO.setCountryDTO(countryDTO);
    }

    public void setContinentDTOForHotelDTO(Trip trip, CountryDTO countryDTO) {
        ContinentDTO continentDTO = new ContinentDTO();
        continentDTO.setName(trip.getHotel().getCity().getCountry().getContinent().getName());
        countryDTO.setContinentDTO(continentDTO);
    }

    public void setRoomDTOSet(Trip trip, HotelDTO hotelDTO) {
        Set<RoomDTO> roomDTOSet = new HashSet<>();
        for (Room room : trip.getHotel().getRoomSet()) {
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setRoomType(room.getRoomType());
            roomDTO.setExtraBed(room.isExtraBed());
            roomDTO.setAvailableRooms(room.getAvailableRooms());
            roomDTOSet.add(roomDTO);
        }
        hotelDTO.setRoomDTOSet(roomDTOSet);
    }

    public TripDTO findTripByName(String tripName) {
        Trip tripFound = tripDAO.findByName(tripName);
        if (tripFound == null) {
            return null;
        }
        return getTripDTO(tripFound);
    }

    public List<TripDTO> findAllTrips() {
        List<Trip> tripList = tripDAO.findAllTrips();
        List<TripDTO> tripDTOList = new LinkedList<>();
        for (Trip trip : tripList) {
            TripDTO tripDTO = getTripDTO(trip);
            tripDTOList.add(tripDTO);
        }
        return tripDTOList;
    }

    public List<TripDTO> findTripsByContinentName(String continentName) {
        List<Trip> tripList = tripDAO.findTripsByContinentName(continentName);
        List<TripDTO> tripDTOList = new LinkedList<>();
        for (Trip trip : tripList) {
            TripDTO tripDTO = getTripDTO(trip);
            tripDTOList.add(tripDTO);
        }
        return tripDTOList;
    }

    public List<TripDTO> findTripsByCountryName(String countryName) {
        List<Trip> tripList = tripDAO.findTripsByCountryName(countryName);
        List<TripDTO> tripDTOList = new LinkedList<>();
        for (Trip trip : tripList) {
            TripDTO tripDTO = getTripDTO(trip);
            tripDTOList.add(tripDTO);
        }
        return tripDTOList;
    }

    public List<TripDTO> findTripsByCityOfStay(String cityName) {
        List<Trip> tripList = tripDAO.findByCityOfStay(cityName);
        List<TripDTO> tripDTOList = new LinkedList<>();
        for (Trip trip : tripList) {
            TripDTO tripDTO = getTripDTO(trip);
            tripDTOList.add(tripDTO);
        }
        return tripDTOList;
    }

    public List<TripDTO> findByHotelOfStay(String hotelName) {
        List<Trip> tripList = tripDAO.findByHotelOfStay(hotelName);
        List<TripDTO> tripDTOList = new LinkedList<>();
        for (Trip trip : tripList) {
            TripDTO tripDTO = getTripDTO(trip);
            tripDTOList.add(tripDTO);
        }
        return tripDTOList;
    }

    public List<TripDTO> findTripsByCityOfDeparture(String cityDepartureName) {
        List<Trip> tripList = tripDAO.findTripsByCityOfDeparture(cityDepartureName);
        List<TripDTO> tripDTOList = new LinkedList<>();
        for (Trip trip : tripList) {
            TripDTO tripDTO = getTripDTO(trip);
            tripDTOList.add(tripDTO);
        }
        return tripDTOList;
    }

    public List<TripDTO> findTripsByAirport(String airportName) {
        List<Trip> tripList = tripDAO.findTripsByAirport(airportName);
        List<TripDTO> tripDTOList = new LinkedList<>();
        for (Trip trip : tripList) {
            TripDTO tripDTO = getTripDTO(trip);
            tripDTOList.add(tripDTO);
        }
        return tripDTOList;
    }

    public List<TripDTO> findTripsByDepartureDate(Date departureDate) {
        List<Trip> tripList = tripDAO.findTripsByDepartureDate(departureDate);
        List<TripDTO> tripDTOList = new LinkedList<>();
        for (Trip trip : tripList) {
            TripDTO tripDTO = getTripDTO(trip);
            tripDTOList.add(tripDTO);
        }
        return tripDTOList;
    }

    public List<TripDTO> findTripsByReturnDate(Date returnDate) {
        List<Trip> tripList = tripDAO.findTripsByReturnDate(returnDate);
        List<TripDTO> tripDTOList = new LinkedList<>();
        for (Trip trip : tripList) {
            TripDTO tripDTO = getTripDTO(trip);
            tripDTOList.add(tripDTO);
        }
        return tripDTOList;
    }

    public List<TripDTO> findTripsByMealType(String mealType) {
        List<Trip> tripList = tripDAO.findTripsByMealType(mealType);
        List<TripDTO> tripDTOList = new LinkedList<>();
        for (Trip trip : tripList) {
            TripDTO tripDTO = getTripDTO(trip);
            tripDTOList.add(tripDTO);
        }
        return tripDTOList;
    }

    public List<TripDTO> findTripsByStandardOfHotel(int hotelStars) {
        List<Trip> tripList = tripDAO.findTripsByStandardOfHotel(hotelStars);
        List<TripDTO> tripDTOList = new LinkedList<>();
        for (Trip trip : tripList) {
            TripDTO tripDTO = getTripDTO(trip);
            tripDTOList.add(tripDTO);
        }
        return tripDTOList;
    }

    public List<TripDTO> findTripsByNumberOfDays(int numberDays) {
        List<Trip> tripList = tripDAO.findTripsByNumberOfDays(numberDays);
        List<TripDTO> tripDTOList = new LinkedList<>();
        for (Trip trip : tripList) {
            TripDTO tripDTO = getTripDTO(trip);
            tripDTOList.add(tripDTO);
        }
        return tripDTOList;
    }

    public List<TripDTO> findTripsByPromoted(boolean promoted) {
        List<Trip> tripList = tripDAO.findTripsByPromoted(promoted);
        List<TripDTO> tripDTOList = new LinkedList<>();
        for (Trip trip : tripList) {
            TripDTO tripDTO = getTripDTO(trip);
            tripDTOList.add(tripDTO);
        }
        return tripDTOList;
    }

    public List<TripDTO> findTripsByMaximPrice(double maxPriceForAdult, double maxPriceForKid) {
        List<Trip> tripList = tripDAO.findTripsByMaximPriceForAdult(maxPriceForAdult, maxPriceForKid);
        List<TripDTO> tripDTOList = new LinkedList<>();
        for (Trip trip : tripList) {
            TripDTO tripDTO = getTripDTO(trip);
            tripDTOList.add(tripDTO);
        }
        return tripDTOList;
    }

    public List<TripDTO> findRecentlyPurchasedTrips(Date referenceDate) {
        List<Trip> tripList = tripDAO.findRecentlyPurchasedTrips(referenceDate);
        List<TripDTO> tripDTOList = new LinkedList<>();
        for (Trip trip : tripList) {
            TripDTO tripDTO = getTripDTO(trip);
            tripDTOList.add(tripDTO);
        }
        return tripDTOList;
    }

    public void updateTripStock(int stock) {
        tripDAO.updateTripStock(stock);
    }

    public int updateAdultPriceAndKidPriceByTripName(double adultPrice, double kidPrice, String tripName) {
        return tripDAO.updateAdultPriceAndKidPriceByTripName(adultPrice, kidPrice, tripName);
    }

    public int deleteTripByName(String tripName) {
        return tripDAO.deleteTripByName(tripName);
    }

    public boolean checkTripAvailability(TripDTO tripDTO) {
        return tripDTO.getStock() > 0;
    }

    private void getTripDTOFromRow() {
    }

    //here I enter a complete list of trips from an excel file
    public void insertTripDTOList(XSSFWorkbook workbook) throws IOException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        XSSFSheet sheet = workbook.getSheet("trip");
        try {
            Iterator<Row> rowIterator = sheet.iterator();
            Row row = rowIterator.next();
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                cellIterator.next();
                Cell cell = cellIterator.next();
                TripDTO tripDTO = new TripDTO();
                tripDTO.setName(cell.getStringCellValue());
                cell = cellIterator.next();
                AirportDTO airportDTO = new AirportDTO();
                airportDTO.setName(cell.getStringCellValue());
                cell = cellIterator.next();
                CityDTO cityDTO = new CityDTO();
                cityDTO.setName(cell.getStringCellValue());
                cell = cellIterator.next();
                CountryDTO countryDTO = new CountryDTO();
                countryDTO.setName(cell.getStringCellValue());
                cell = cellIterator.next();
                ContinentDTO continentDTO = new ContinentDTO();
                continentDTO.setName(cell.getStringCellValue());
                cell = cellIterator.next();
                Set<FlightDTO> flightDTOSet = new HashSet<>();
                FlightDTO flightDTO = new FlightDTO();
                java.util.Date date = cell.getDateCellValue();
                Date dateSQL = new Date(date.getTime());
                flightDTO.setFlightDepartureDate(dateSQL);
                cell = cellIterator.next();
                date = cell.getDateCellValue();
                Timestamp timestamp = new Timestamp(date.getTime());
                flightDTO.setFlightDepartureTime(timestamp);
                cell = cellIterator.next();
                date = cell.getDateCellValue();
                dateSQL = new Date(date.getTime());
                flightDTO.setFlightReturnDate(dateSQL);
                cell = cellIterator.next();
                date = cell.getDateCellValue();
                timestamp = new Timestamp(date.getTime());
                flightDTO.setFlightReturnTime(timestamp);
                cell = cellIterator.next();
                flightDTO.setFlightTo(cell.getStringCellValue());
                cell = cellIterator.next();
                flightDTO.setPrice(cell.getNumericCellValue());
                cell = cellIterator.next();
                flightDTO.setAvailableSeats((int) cell.getNumericCellValue());
                flightDTOSet.add(flightDTO);
                countryDTO.setContinentDTO(continentDTO);
                cityDTO.setCountryDTO(countryDTO);
                airportDTO.setCityDTO(cityDTO);
                airportDTO.setFlightDTOSet(flightDTOSet);
                tripDTO.setAirportDTO(airportDTO);
                cell = cellIterator.next();
                HotelDTO hotelDTO = new HotelDTO();
                hotelDTO.setName(cell.getStringCellValue());
                cell = cellIterator.next();
                hotelDTO.setStars((int) cell.getNumericCellValue());
                cell = cellIterator.next();
                hotelDTO.setDescription(cell.getStringCellValue());
                cell = cellIterator.next();
                CityDTO cityDTOForHotelDTO = new CityDTO();
                cityDTOForHotelDTO.setName(cell.getStringCellValue());
                cell = cellIterator.next();
                CountryDTO countryDTOForHotelDTO = new CountryDTO();
                countryDTOForHotelDTO.setName(cell.getStringCellValue());
                cell = cellIterator.next();
                ContinentDTO continentDTOForHotelDTO = new ContinentDTO();
                continentDTOForHotelDTO.setName(cell.getStringCellValue());
                cell = cellIterator.next();
                Set<RoomDTO> roomDTOSet = new HashSet<>();
                RoomDTO singleRoomDTO = new RoomDTO();
                singleRoomDTO.setRoomType(cell.getStringCellValue());
                cell = cellIterator.next();
                singleRoomDTO.setAvailableRooms((int) cell.getNumericCellValue());
                cell = cellIterator.next();
                singleRoomDTO.setExtraBed(Boolean.parseBoolean(cell.toString()));
                roomDTOSet.add(singleRoomDTO);
                cell = cellIterator.next();
                RoomDTO doubleRoomDTO = new RoomDTO();
                doubleRoomDTO.setRoomType(cell.getStringCellValue());
                cell = cellIterator.next();
                doubleRoomDTO.setAvailableRooms((int) cell.getNumericCellValue());
                cell = cellIterator.next();
                doubleRoomDTO.setExtraBed(Boolean.parseBoolean(cell.toString()));
                roomDTOSet.add(doubleRoomDTO);
                cell = cellIterator.next();
                RoomDTO familyRoomDTO = new RoomDTO();
                familyRoomDTO.setRoomType(cell.getStringCellValue());
                cell = cellIterator.next();
                familyRoomDTO.setAvailableRooms((int) cell.getNumericCellValue());
                cell = cellIterator.next();
                familyRoomDTO.setExtraBed(Boolean.parseBoolean(cell.toString()));
                roomDTOSet.add(familyRoomDTO);
                cell = cellIterator.next();
                RoomDTO apartmentDTO = new RoomDTO();
                apartmentDTO.setRoomType(cell.getStringCellValue());
                cell = cellIterator.next();
                apartmentDTO.setAvailableRooms((int) cell.getNumericCellValue());
                cell = cellIterator.next();
                apartmentDTO.setExtraBed(Boolean.parseBoolean(cell.toString()));
                roomDTOSet.add(apartmentDTO);
                countryDTOForHotelDTO.setContinentDTO(continentDTOForHotelDTO);
                cityDTOForHotelDTO.setCountryDTO(countryDTOForHotelDTO);
                hotelDTO.setCityDTO(cityDTOForHotelDTO);
                hotelDTO.setRoomDTOSet(roomDTOSet);
                tripDTO.setHotelDTO(hotelDTO);
                cell = cellIterator.next();
                date = cell.getDateCellValue();
                dateSQL = new Date(date.getTime());
                tripDTO.setDepartureDate(dateSQL);
                cell = cellIterator.next();
                date = cell.getDateCellValue();
                dateSQL = new Date(date.getTime());
                tripDTO.setReturnDate(dateSQL);
                cell = cellIterator.next();
                tripDTO.setNumberDays((int) cell.getNumericCellValue());
                cell = cellIterator.next();
                tripDTO.setMealType(cell.getStringCellValue());
                cell = cellIterator.next();
                tripDTO.setAdultPrice(cell.getNumericCellValue());
                cell = cellIterator.next();
                tripDTO.setKidPrice(cell.getNumericCellValue());
                cell = cellIterator.next();
                tripDTO.setPromoted(Boolean.parseBoolean(cell.toString()));
                cell = cellIterator.next();
                tripDTO.setStock((int) cell.getNumericCellValue());
                Trip trip = getTrip(tripDTO, session);
                long numberOfTrips = tripDAO.countTripByName(trip.getName());
                if (numberOfTrips != 0) {
                    System.out.println(trip.getName() + " already exists in the database.");
                } else {
                    tripDAO.insert(trip, session);
                }
            }
            workbook.close();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        session.getTransaction().commit();
        session.close();
    }
}
