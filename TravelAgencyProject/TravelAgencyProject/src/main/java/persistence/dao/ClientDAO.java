package persistence.dao;

import persistence.config.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import persistence.entities.Client;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ClientDAO {

    //to create the method findByLoggedIn

    public void insert(Client client) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(client);
        session.getTransaction().commit();
        session.close();
    }

    public Client findClientByUsernameAndPassword(String username, String password) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findClientByUsernameAndPasswordQuery = session.createNamedQuery("findClientByUsernameAndPassword");
        findClientByUsernameAndPasswordQuery.setParameter("username", username);
        findClientByUsernameAndPasswordQuery.setParameter("password", password);
        Client clientFound = null;
        try {
            clientFound = (Client) findClientByUsernameAndPasswordQuery.getSingleResult();
        } catch (NoResultException exception) {
            System.out.println(exception.getMessage());
        }
        session.getTransaction().commit();
        session.close();
        return clientFound;
    }

    public Client findClientByEmail(String email){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findClientByEmail = session.createNamedQuery("findClientByEmail");
        findClientByEmail.setParameter("email", email);
        Client clientFound = null;
        try {
            clientFound = (Client) findClientByEmail.getSingleResult();
        } catch (NoResultException exception) {
            System.out.println(exception.getMessage());
        }
        session.getTransaction().commit();
        session.close();
        return clientFound;
    }

    public Client findClientByUsername(String username) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findClientByUsernameQuery = session.createNamedQuery("findClientByUsername");
        findClientByUsernameQuery.setParameter("username", username);
        Client clientFound = null;
        try {
            clientFound = (Client) findClientByUsernameQuery.getSingleResult();
        } catch (NoResultException exception) {
            System.out.println(exception.getMessage());
        }
        session.getTransaction().commit();
        session.close();
        return clientFound;
    }

    public List<Client> findByYearOfBirth(int yearOfBirth) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findClientsByYearOfBirth = session.createNamedQuery("findClientsByYearOfBirth");
        findClientsByYearOfBirth.setParameter("yearOfBirth", yearOfBirth);
        List<Client> clientList = findClientsByYearOfBirth.getResultList();
        session.getTransaction().commit();
        session.close();
        return clientList;
    }

    public int deleteClientByUsername(String username) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query deleteClientQuery = session.createNamedQuery("deleteClientByUsername");
        deleteClientQuery.setParameter("username", username);
        int numberOfClientsDeleted = deleteClientQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return numberOfClientsDeleted;
    }

    public List<Client> findClientsBySurnameAndFirstName(String surname, String firstName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findClientsBySurnameAndFirstNameQuery = session.createNamedQuery("findClientsBySurnameAndFirstName");
        findClientsBySurnameAndFirstNameQuery.setParameter("surname", surname);
        findClientsBySurnameAndFirstNameQuery.setParameter("firstName", firstName);
        List<Client> clientList = findClientsBySurnameAndFirstNameQuery.getResultList();
        session.getTransaction().commit();
        session.close();
        return clientList;
    }



}
