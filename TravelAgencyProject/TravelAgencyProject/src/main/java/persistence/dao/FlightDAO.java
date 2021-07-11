package persistence.dao;

import persistence.config.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import persistence.entities.Flight;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.sql.Date;

@Repository
public class FlightDAO {

    // I didn't do the insert method because I enter the flight from airport

    public Flight findFlightByDestinationAndByDepartureDateAndByAirport(String destination, Date flightDepartureDate, String airportName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findFlightQuery = session.createNamedQuery("findFlight");
        findFlightQuery.setParameter("destination", destination);
        findFlightQuery.setParameter("flightDepartureDate", flightDepartureDate);
        findFlightQuery.setParameter("airportName", airportName);
        Flight flightFound = null;
        try {
            flightFound = (Flight) findFlightQuery.getSingleResult();
        } catch (NoResultException exception) {
            System.out.println(exception.getMessage());
        }
        session.getTransaction().commit();
        session.close();
        return flightFound;
    }

    public Long countFlightsByDestinationAndByDepartureDateAndByAirport(String destination, Date flightDepartureDate, String airportName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query countFlights = session.createNamedQuery("countFlights");
        countFlights.setParameter("destination", destination);
        countFlights.setParameter("flightDepartureDate", flightDepartureDate);
        countFlights.setParameter("airportName", airportName);
        Long numberOfFlights = (Long) countFlights.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return numberOfFlights;
    }

    public int updateAvailableSeatsNumber(int numberOfPersons, String destination, Date flightDepartureDate, String airportName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query updateAvailableSeatsQuery = session.createNamedQuery("updateAvailableSeats");
        updateAvailableSeatsQuery.setParameter("numberOfPersons", numberOfPersons);
        updateAvailableSeatsQuery.setParameter("destination", destination);
        updateAvailableSeatsQuery.setParameter("flightDepartureDate", flightDepartureDate);
        updateAvailableSeatsQuery.setParameter("airportName", airportName);
        int numberOfFlightsUpdated = updateAvailableSeatsQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return numberOfFlightsUpdated;
    }

    public void updateAvailableSeats(int numberOfPersons, String destination, Date flightDepartureDate, String airportName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query updateAvailableSeatsQuery = session.createNamedQuery("updateAvailableSeats");
        updateAvailableSeatsQuery.setParameter("numberOfPersons", numberOfPersons);
        updateAvailableSeatsQuery.setParameter("destination", destination);
        updateAvailableSeatsQuery.setParameter("flightDepartureDate", flightDepartureDate);
        updateAvailableSeatsQuery.setParameter("airportName", airportName);
        updateAvailableSeatsQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public int deleteFlight(String destination, Date flightDepartureDate, String airportName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query deleteFlightQuery = session.createNamedQuery("deleteFlight");
        deleteFlightQuery.setParameter("destination", destination);
        deleteFlightQuery.setParameter("flightDepartureDate", flightDepartureDate);
        deleteFlightQuery.setParameter("airportName", airportName);
        int numberOfFlightsDeleted = deleteFlightQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return numberOfFlightsDeleted;
    }
}
