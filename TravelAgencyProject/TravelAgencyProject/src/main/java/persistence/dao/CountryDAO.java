package persistence.dao;

import persistence.config.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import persistence.entities.Country;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CountryDAO {

    public void insert(Country country) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(country);
        session.getTransaction().commit();
        session.close();
    }

    public void insert(Country country, Session session) {
        session.save(country);
    }

    public Long countCountriesByName(String countryName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query countCountriesByNameQuery = session.createNamedQuery("countCountriesByName");
        countCountriesByNameQuery.setParameter("name", countryName);
        Long numberOfCountriesByName = (Long) countCountriesByNameQuery.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return numberOfCountriesByName;
    }

    public Country findCountryByName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findCountryByNameQuery = session.createNamedQuery("findCountryByName");
        findCountryByNameQuery.setParameter("name", name);
        Country countryByName = null;
        try {
            countryByName = (Country) findCountryByNameQuery.getSingleResult();
        } catch (NoResultException exception) {
            System.out.println(exception.getMessage());
        }
        session.getTransaction().commit();
        session.close();
        return countryByName;
    }

    public Country findCountryByName(String name, Session session) {
        Query findCountryByNameQuery = session.createNamedQuery("findCountryByName");
        findCountryByNameQuery.setParameter("name", name);
        Country countryByName = null;
        try {
            countryByName = (Country) findCountryByNameQuery.getSingleResult();
        } catch (NoResultException exception) {
            System.out.println(exception.getMessage());
        }
        return countryByName;
    }

    public List<Country> findCountriesByContinentName(String continentName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findCountriesByContinentNameQuery = session.createNamedQuery("findCountriesByContinentName");
        findCountriesByContinentNameQuery.setParameter("continentName", continentName);
        List<Country> countriesListByContinentName = findCountriesByContinentNameQuery.getResultList();
        session.getTransaction().commit();
        session.close();
        return countriesListByContinentName;
    }

    public Integer deleteCountryByName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query deleteCountryQuery = session.createNamedQuery("deleteCountryByName");
        deleteCountryQuery.setParameter("name", name);
        Integer numberOfCountriesDeleted = deleteCountryQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return numberOfCountriesDeleted;
    }

    public Integer updateCountryName(String currentName, String newName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query updateCountryQuery = session.createNamedQuery("updateCountryName");
        updateCountryQuery.setParameter("currentName", currentName);
        updateCountryQuery.setParameter("newName", newName);
        Integer numberOfCountriesUpdated = updateCountryQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return numberOfCountriesUpdated;
    }

    public List<Country> findAllCountries() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findAllCountriesQuery = session.createNamedQuery("findAllCountries");
        List<Country> countryList = findAllCountriesQuery.getResultList();
        session.getTransaction().commit();
        session.close();
        return countryList;
    }

}
