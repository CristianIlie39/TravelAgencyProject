package persistence.dao;

import persistence.config.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import persistence.entities.Trip;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Repository
public class TripDAO {

    public void insert(Trip trip) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(trip);
        session.getTransaction().commit();
        session.close();
    }

    public void insert(Trip trip, Session session) {
        session.save(trip);
    }

    public Trip findByName(String tripName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findByNameQuery = session.createNamedQuery("findByName");
        findByNameQuery.setParameter("name", tripName);
        Trip tripFound = null;
        try{
            tripFound = (Trip) findByNameQuery.getSingleResult();
        } catch (NoResultException exception) {
            System.out.println(exception.getMessage());
        }
        session.getTransaction().commit();
        session.close();
        return tripFound;
    }

    public long countTripByName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query countTripByName = session.createNamedQuery("countTripByName");
        countTripByName.setParameter("name", name);
        long numberOfTrips = (long) countTripByName.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return numberOfTrips;
    }


    public List<Trip> findAllTrips() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findAllTrips = session.createNamedQuery("findAllTrips");
        List<Trip> tripList = findAllTrips.getResultList();
        session.getTransaction().commit();
        session.close();
        return tripList;
    }


    public List<Trip> findTripsByContinentName(String continentName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findByContinent = session.createNamedQuery("findTripsByContinentName");
        findByContinent.setParameter("continentName", continentName);
        List<Trip> tripList = findByContinent.getResultList();
        session.getTransaction();
        session.close();
        return tripList;
    }

    public List<Trip> findTripsByCountryName(String countryName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findByCountry = session.createNamedQuery("findTripsByCountryName");
        findByCountry.setParameter("countryName", countryName);
        List<Trip> tripList = findByCountry.getResultList();
        session.getTransaction().commit();
        session.close();
        return tripList;
    }

    public List<Trip> findByCityOfStay(String cityName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findByCity = session.createNamedQuery("findTripsByCity");
        findByCity.setParameter("cityName", cityName);
        List<Trip> tripList = findByCity.getResultList();
        session.getTransaction().commit();
        session.close();
        return tripList;
    }

    public List<Trip> findByHotelOfStay(String hotelName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findByHotel = session.createNamedQuery("findTripsByHotel");
        findByHotel.setParameter("hotelName", hotelName);
        List<Trip> tripList = findByHotel.getResultList();
        session.getTransaction().commit();
        session.close();
        return tripList;
    }

    public List<Trip> findTripsByCityOfDeparture(String cityDepartureName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findTripsByCityOfDeparture = session.createNamedQuery("findTripsByCityOfDeparture");
        findTripsByCityOfDeparture.setParameter("cityDepartureName", cityDepartureName);
        List<Trip> tripList = findTripsByCityOfDeparture.getResultList();
        session.getTransaction().commit();
        session.close();
        return tripList;
    }

    public List<Trip> findTripsByAirport(String airportName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findTripsByAirport = session.createNamedQuery("findTripsByAirportName");
        findTripsByAirport.setParameter("airportName", airportName);
        List<Trip> tripList = findTripsByAirport.getResultList();
        session.getTransaction().commit();
        session.close();
        return tripList;
    }

    public List<Trip> findTripsByDepartureDate(Date departureDate) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findByDepartureDate = session.createNamedQuery("findTripsByDepartureDate");
        findByDepartureDate.setParameter("departureDate", departureDate);
        List<Trip> tripList = findByDepartureDate.getResultList();
        session.getTransaction().commit();
        session.close();
        return tripList;
    }

    public List<Trip> findTripsByReturnDate(Date returnDate) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findTripsByReturnDate = session.createNamedQuery("findTripsByReturnDate");
        findTripsByReturnDate.setParameter("returnDate", returnDate);
        List<Trip> tripList = findTripsByReturnDate.getResultList();
        session.getTransaction().commit();
        session.close();
        return tripList;
    }

    public List<Trip> findTripsByMealType(String mealType) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findTripsByMealType = session.createNamedQuery("findTripsByMealType");
        findTripsByMealType.setParameter("mealType", mealType);
        List<Trip> tripList = findTripsByMealType.getResultList();
        session.getTransaction().commit();
        session.close();
        return tripList;
    }

    public List<Trip> findTripsByStandardOfHotel(int hotelStars) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findTripsByStandardOfHotel = session.createNamedQuery("findTripsByStandardOfHotel");
        findTripsByStandardOfHotel.setParameter("stars", hotelStars);
        List<Trip> tripList = findTripsByStandardOfHotel.getResultList();
        session.getTransaction().commit();
        session.close();
        return tripList;
    }

    public List<Trip> findTripsByNumberOfDays(int numberDays) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findTripsByNumberOfDays = session.createNamedQuery("findTripsByNumberOfDays");
        findTripsByNumberOfDays.setParameter("numberDays", numberDays);
        List<Trip> tripList = findTripsByNumberOfDays.getResultList();
        session.getTransaction().commit();
        session.close();
        return tripList;
    }

    public List<Trip> findTripsByPromoted(boolean promoted) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findTripsByPromoted = session.createNamedQuery("findTripsByPromoted");
        findTripsByPromoted.setParameter("promoted", promoted);
        List<Trip> tripList = findTripsByPromoted.getResultList();
        session.getTransaction().commit();
        session.close();
        return tripList;
    }

    public List<Trip> findTripsByMaximPriceForAdult(double maxPriceForAdult, double maxPriceForKid) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findTripsByMaximPrice = session.createNamedQuery("findTripsByMaximPriceForAdult");
        findTripsByMaximPrice.setParameter("maxPriceForAdult", maxPriceForAdult);
        findTripsByMaximPrice.setParameter("maxPriceForKid", maxPriceForKid);
        List<Trip> tripList = findTripsByMaximPrice.getResultList();
        session.getTransaction().commit();
        session.close();
        return tripList;
    }

    public List<Trip> findRecentlyPurchasedTrips(Date referenceDate) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findRecentlyPurchasedTripsQuery = session.createNamedQuery("findRecentlyPurchasedTrips");
        findRecentlyPurchasedTripsQuery.setParameter("referenceDate", referenceDate);
        Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        Date currentDate = new Date(date.getTime());
        findRecentlyPurchasedTripsQuery.setParameter("currentDate", currentDate);
        List<Trip> tripList = findRecentlyPurchasedTripsQuery.getResultList();
        session.getTransaction().commit();
        session.close();
        return tripList;
    }

    public void updateTripStock(int stock) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query updateTripStockQuery = session.createNamedQuery("updateTripStock");
        updateTripStockQuery.setParameter("stock", stock);
        updateTripStockQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public int updateAdultPriceAndKidPriceByTripName(double adultPrice, double kidPrice, String tripName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query updateAdultPriceAndKidPriceByTripNameQuery = session.createNamedQuery("updateAdultPriceAndKidPriceByTripName");
        updateAdultPriceAndKidPriceByTripNameQuery.setParameter("adultPrice", adultPrice);
        updateAdultPriceAndKidPriceByTripNameQuery.setParameter("kidPrice", kidPrice);
        updateAdultPriceAndKidPriceByTripNameQuery.setParameter("name", tripName);
        int numberOfTripsUpdated = updateAdultPriceAndKidPriceByTripNameQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return numberOfTripsUpdated;
    }

    public int deleteTripByName(String tripName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query deleteTripQuery = session.createNamedQuery("deleteTrip");
        deleteTripQuery.setParameter("name", tripName);
        int numberOfTripsDeleted = deleteTripQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return numberOfTripsDeleted;
    }
}
