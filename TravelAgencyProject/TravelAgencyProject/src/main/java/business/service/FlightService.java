package business.service;

import business.dto.FlightDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.dao.FlightDAO;
import persistence.entities.Flight;
import java.sql.Date;

@Service
public class FlightService {

    @Autowired
    FlightDAO flightDAO;

    public FlightDTO findFlightByDestinationAndByDepartureDateAndByAirport(String flightDestination, Date flightDepartureDate, String airportName) {
        Flight flightFound = flightDAO.findFlightByDestinationAndByDepartureDateAndByAirport(flightDestination, flightDepartureDate, airportName);
        if (flightFound == null) {
            return null;
        }
        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setFlightDepartureDate(flightFound.getFlightDepartureDate());
        flightDTO.setFlightDepartureTime(flightFound.getFlightDepartureTime());
        flightDTO.setFlightReturnDate(flightFound.getFlightReturnDate());
        flightDTO.setFlightReturnTime(flightFound.getFlightReturnTime());
        flightDTO.setFlightTo(flightFound.getFlightTo());
        flightDTO.setPrice(flightFound.getPrice());
        flightDTO.setAvailableSeats(flightFound.getAvailableSeats());
        return flightDTO;
    }

    public Long countFlightsByDestinationAndByDepartureDateAndByAirport(String destination, Date flightDepartureDate, String airportName) {
        return flightDAO.countFlightsByDestinationAndByDepartureDateAndByAirport(destination, flightDepartureDate, airportName);
    }

    public int updateAvailableSeatsNumber(int numberOfPersons, String destination, Date flightDepartureDate, String airportName) {
        return flightDAO.updateAvailableSeatsNumber(numberOfPersons,destination, flightDepartureDate, airportName);
    }

    public boolean checkAvalabilityFlightSeats(int availableSeats, int numberOfPersons) {
        return availableSeats >= numberOfPersons;
    }

    public int deleteFlight(String destination, Date flightDepartureDate, String airportName) {
        return flightDAO.deleteFlight(destination, flightDepartureDate, airportName);
    }
}
