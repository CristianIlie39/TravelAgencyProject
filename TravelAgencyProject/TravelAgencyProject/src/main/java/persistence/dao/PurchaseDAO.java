package persistence.dao;

import persistence.config.HibernateUtil;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import persistence.entities.Purchase;

import javax.persistence.Query;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Repository
public class PurchaseDAO {

    public void insert(Purchase purchase) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(purchase);
        session.getTransaction().commit();
        session.close();
    }

    public List<Purchase> findRecentlyPurchases(Date referenceDate) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findRecentlyPurchasesQuery = session.createNamedQuery("findRecentlyPurchases");
        findRecentlyPurchasesQuery.setParameter("referenceDate", referenceDate);
        Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        Date currentDate = new Date(date.getTime());
        findRecentlyPurchasesQuery.setParameter("currentDate", currentDate);
        List<Purchase> purchaseList = findRecentlyPurchasesQuery.getResultList();
        session.getTransaction().commit();
        session.close();
        return purchaseList;
    }

    public List<Purchase> findPurchasesByUsername(String username) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findPurchasesByUsernameQuery = session.createNamedQuery("findPurchasesByUsername");
        findPurchasesByUsernameQuery.setParameter("username", username);
        List<Purchase> purchaseList = findPurchasesByUsernameQuery.getResultList();
        session.getTransaction().commit();
        session.close();
        return purchaseList;
    }

    public List<Purchase> findByDateOfPurchase (Date dateOfPurchase) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findPurchaseByDate = session.createNamedQuery("findByDateOfPurchase");
        findPurchaseByDate.setParameter("dateOfPurchase", dateOfPurchase);
        List<Purchase> purchaseList = findPurchaseByDate.getResultList();
        session.getTransaction().commit();
        session.close();
        return purchaseList;
    }

    public Purchase findPurchaseByUsernameAndTripNameQuery(String username, String tripName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query findPurchaseByUsernameAndTripNameQuery = session.createNamedQuery("findPurchaseByUsernameAndTripNameQuery");
        findPurchaseByUsernameAndTripNameQuery.setParameter("username", username);
        Purchase purchaseList = (Purchase) findPurchaseByUsernameAndTripNameQuery.getSingleResult();
        session.getTransaction().commit();
        session.close();
        return purchaseList;
    }


}
