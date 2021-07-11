package persistence.dao;

import persistence.config.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import persistence.entities.City;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CityDAO {

    public void insert(City city) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(city);
        session.getTransaction().commit();
        session.close();
    }

    public void insert(City city, Session session) {
        session.save(city);
    }

    public Long countCityByName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query countCityQuery = session.createNamedQuery("countCityByName");
        countCityQuery.setParameter("name", name);
        Long numberOfCitiesFound = (Long) countCityQuery.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return numberOfCitiesFound;
    }

    public City findCityByName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findCityQuery = session.createNamedQuery("findCity");
        findCityQuery.setParameter("name", name);
        City cityFound = null;
        try {
            cityFound = (City) findCityQuery.getSingleResult();
        } catch (NoResultException exception) {
            System.out.println(exception.getMessage());
        }
        session.getTransaction().commit();
        session.close();
        return cityFound;
    }

    public City findCityByName(String name, Session session) {
        Query findCityQuery = session.createNamedQuery("findCity");
        findCityQuery.setParameter("name", name);
        City cityFound = null;
        try {
            cityFound = (City) findCityQuery.getSingleResult();
        } catch (NoResultException exception) {
            System.out.println(exception.getMessage());
        }
        return cityFound;
    }

    public Integer deleteCityByName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query deleteCityQuery = session.createNamedQuery("deleteCityByName");
        deleteCityQuery.setParameter("name", name);
        Integer numberOfCitiesDeleted = deleteCityQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return numberOfCitiesDeleted;
    }

    public List<City> findCitiesByCountryName(String countryName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findCitiesQuery = session.createNamedQuery("findCitiesByCountry");
        findCitiesQuery.setParameter("name", countryName);
        List<City> cityList = findCitiesQuery.getResultList();
        session.getTransaction().commit();
        session.close();
        return cityList;
    }

    public List<City> findAllCities() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findAllCitiesQuery = session.createNamedQuery("findAllCities");
        List<City> cityList = findAllCitiesQuery.getResultList();
        session.getTransaction().commit();
        session.close();
        return cityList;
    }

    public Integer updateCityName(String currentName, String newName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query updateCityNameQuery = session.createNamedQuery("updateCityName");
        updateCityNameQuery.setParameter("currentName", currentName);
        updateCityNameQuery.setParameter("newName", newName);
        Integer numberOfCitiesUpdated = updateCityNameQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return numberOfCitiesUpdated;
    }

}
