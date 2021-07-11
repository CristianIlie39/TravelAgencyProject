package persistence.dao;

import persistence.config.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import persistence.entities.Continent;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ContinentDAO {

    public void insert(Continent continent) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(continent);
        session.getTransaction().commit();
        session.close();
    }

    public void insert(Continent continent, Session session) {
        session.save(continent);
    }

    public Continent findContinentByName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findContinentQuery = session.createNamedQuery("findContinentByName");
        findContinentQuery.setParameter("name", name);
        Continent continentByName = null;
        try {
            continentByName = (Continent) findContinentQuery.getSingleResult();
        } catch (NoResultException exception) {
            System.out.println(exception.getMessage());
        }
        session.getTransaction().commit();
        session.close();
        return continentByName;
    }

    public Continent findContinentByName(String name, Session session) {
        Query findContinentQuery = session.createNamedQuery("findContinentByName");
        findContinentQuery.setParameter("name", name);
        Continent continentByName = null;
        try {
            continentByName = (Continent) findContinentQuery.getSingleResult();
        } catch (NoResultException exception) {
            System.out.println(exception.getMessage());
        }
        return continentByName;
    }

    public List<Continent> findAllContinents() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findAllQuery = session.createNamedQuery("findAllContinents");
        List<Continent> continentList = findAllQuery.getResultList();
        session.getTransaction().commit();
        session.close();
        return continentList;
    }

    public Long countContinentsByName(String continentName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query countContinentsByName = session.createNamedQuery("countContinentsByName");
        countContinentsByName.setParameter("name", continentName);
        Long continentsNumberByName = (Long) countContinentsByName.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return continentsNumberByName;
    }

    public Integer deleteContinentByName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query deleteContinentByName = session.createNamedQuery("deleteContinentByName");
        deleteContinentByName.setParameter("name", name);
        Integer numberOfContinentsDeleted = deleteContinentByName.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return numberOfContinentsDeleted;
    }

    public Integer updateContinentName(String currentName, String newName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query updateContinentNameQuery = session.createNamedQuery("updateContinentName");
        updateContinentNameQuery.setParameter("currentName", currentName);
        updateContinentNameQuery.setParameter("newName", newName);
        Integer numberOfContinentsUpdated = updateContinentNameQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return numberOfContinentsUpdated;
    }
}
