package persistence.dao;

import persistence.config.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;

@Repository
public class AccountDAO {

    //I didn't do the insert method because I enter the account fom client

    public int deleteAccountByUsername(String username) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query deleteAccountByUsername = session.createNamedQuery("deleteAccountByUsername");
        deleteAccountByUsername.setParameter("username", username);
        int numberOfAccountsDeleted = deleteAccountByUsername.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return numberOfAccountsDeleted;
    }

    public int updateLogInUser(boolean loggedIn, String username) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query updateLogInUserQuery = session.createNamedQuery("updateLogInUser");
        updateLogInUserQuery.setParameter("loggedIn", loggedIn);
        updateLogInUserQuery.setParameter("username", username);
        int numberOfUsersUpdated = updateLogInUserQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return numberOfUsersUpdated;
    }

    public int changeUsername(String newUsername, String currentUsername) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query changeUsernameQuery = session.createNamedQuery("changeUsername");
        changeUsernameQuery.setParameter("newUsername", newUsername);
        changeUsernameQuery.setParameter("currentUsername", currentUsername);
        int numberOfUsernameChanged = changeUsernameQuery.executeUpdate();
        session.getTransaction().commit();
        session.close();
        return numberOfUsernameChanged;
    }

}
