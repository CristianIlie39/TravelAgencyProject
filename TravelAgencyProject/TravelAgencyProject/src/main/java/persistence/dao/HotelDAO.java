package persistence.dao;

import business.dto.CityDTO;
import persistence.config.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import persistence.entities.Hotel;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class HotelDAO {

    public void insert(Hotel hotel) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(hotel);
        session.getTransaction().commit();
        session.close();
    }

    public void insert(Hotel hotel, Session session) {
        session.save(hotel);
    }

    public List<Hotel> findAllHotelsByCity(String cityName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findAllHotelsByCityQuery = session.createNamedQuery("findAllHotelsByCity");
        findAllHotelsByCityQuery.setParameter("name", cityName);
        List<Hotel> hotelList = findAllHotelsByCityQuery.getResultList();
        session.getTransaction().commit();
        session.close();
        return hotelList;
    }

    public Hotel findHotelByNameAndByCityName(String hotelName, String cityName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findHotelByNameAndByCityNameQuery = session.createNamedQuery("findHotelByNameAndByCityName");
        findHotelByNameAndByCityNameQuery.setParameter("hotelName", hotelName);
        findHotelByNameAndByCityNameQuery.setParameter("cityName", cityName);
        Hotel hotelByNameAndByCityName = null;
        try {
            hotelByNameAndByCityName = (Hotel) findHotelByNameAndByCityNameQuery.getSingleResult();
        } catch (NoResultException exception) {
            System.out.println(exception.getMessage());
        }
        session.getTransaction().commit();
        session.close();
        return hotelByNameAndByCityName;
    }

    public Hotel findHotelByNameAndByCityName(String hotelName, String cityName, Session session) {
        Query findHotelByNameAndByCityNameQuery = session.createNamedQuery("findHotelByNameAndByCityName");
        findHotelByNameAndByCityNameQuery.setParameter("hotelName", hotelName);
        findHotelByNameAndByCityNameQuery.setParameter("cityName", cityName);
        Hotel hotelByNameAndByCityName = null;
        try {
            hotelByNameAndByCityName = (Hotel) findHotelByNameAndByCityNameQuery.getSingleResult();
        } catch (NoResultException exception) {
            System.out.println(exception.getMessage());
        }
        return hotelByNameAndByCityName;
    }

    public Long countHotelsByNameAndByCityName(String hotelName, String cityName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query countHotelsQuery = session.createNamedQuery("countHotelsByNameAndByCityName");
        countHotelsQuery.setParameter("hotelName", hotelName);
        countHotelsQuery.setParameter("cityName", cityName);
        Long numberOfHotelsFound = (Long) countHotelsQuery.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return numberOfHotelsFound;
    }

    public List<Hotel> findHotelsByStarsInCity(int stars, CityDTO cityDTO) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findHotelsByStarsQuery = session.createNamedQuery("findHotelsByStarsInCity");
        findHotelsByStarsQuery.setParameter("stars", stars);
        findHotelsByStarsQuery.setParameter("cityName", cityDTO.getName());
        findHotelsByStarsQuery.setParameter("countryName", cityDTO.getCountryDTO().getName());
        findHotelsByStarsQuery.setParameter("continentName", cityDTO.getCountryDTO().getContinentDTO().getName());
        List<Hotel> hotelList = findHotelsByStarsQuery.getResultList();
        session.getTransaction().commit();
        session.close();
        return hotelList;
    }

    public Integer updateHotelName(String currentName, String newName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query updateHotelNameQuery = session.createNamedQuery("updateHotelName");
        updateHotelNameQuery.setParameter("currentName", currentName);
        updateHotelNameQuery.setParameter("newName", newName);
        Integer numberOfHotelsUpdated = (Integer) updateHotelNameQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return numberOfHotelsUpdated;
    }

    public Integer deleteHotelByName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query deleteHotelQuery = session.createNamedQuery("deleteHotelByName");
        deleteHotelQuery.setParameter("name", name);
        Integer numberOfHotelDeleted = deleteHotelQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return numberOfHotelDeleted;
    }



}
