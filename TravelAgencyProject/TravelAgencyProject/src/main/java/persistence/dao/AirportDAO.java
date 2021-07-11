package persistence.dao;

import persistence.config.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import persistence.entities.Airport;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class AirportDAO {

    public void insert(Airport airport) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(airport);
        session.getTransaction().commit();
        session.close();
    }

    public void insert(Airport airport, Session session) {
        session.save(airport);
    }

    public Long countAirportByNameAndByCityName(String airportName, String cityName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query countAirportQuery = session.createNamedQuery("countAirport");
        countAirportQuery.setParameter("airportName", airportName);
        countAirportQuery.setParameter("cityName", cityName);
        Long numberOfAirports = (Long) countAirportQuery.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return numberOfAirports;
    }

    public Airport findAirportByName(String airportName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findByNameQuery = session.createNamedQuery("findAirportByName");
        findByNameQuery.setParameter("name", airportName);
        Airport airportByName = null;
        try {
            airportByName = (Airport) findByNameQuery.getSingleResult();
        } catch (NoResultException exception) {
            System.out.println(exception.getMessage());
        }
        session.getTransaction().commit();
        session.close();
        return airportByName;
    }

    public Airport findAirportByName(String airportName, Session session) {
        Query findByNameQuery = session.createNamedQuery("findAirportByName");
        findByNameQuery.setParameter("name", airportName);
        Airport airportByName = null;
        try {
            airportByName = (Airport) findByNameQuery.getSingleResult();
        } catch (NoResultException exception) {
            System.out.println(exception.getMessage());
        }
        return airportByName;
    }

    public List<Airport> findAirportsByCityName(String cityName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findAirportsQuery = session.createNamedQuery("findAirportsByCityName");
        findAirportsQuery.setParameter("cityName", cityName);
        List<Airport> airportList = findAirportsQuery.getResultList();
        session.getTransaction().commit();
        session.close();
        return airportList;
    }

    public int updateAirportName(String currentName, String newName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query updateAirportNameQuery = session.createNamedQuery("updateAirportName");
        updateAirportNameQuery.setParameter("currentName", currentName);
        updateAirportNameQuery.setParameter("newName", newName);
        int numberOfAirportsUpdated = updateAirportNameQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return numberOfAirportsUpdated;
    }

    public int deleteAirportByName(String airportName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query deleteAirportQuery = session.createNamedQuery("deleteAirportByName");
        deleteAirportQuery.setParameter("airportName", airportName);
        int numberOfAirportsDeleted = deleteAirportQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return numberOfAirportsDeleted;
    }
}
