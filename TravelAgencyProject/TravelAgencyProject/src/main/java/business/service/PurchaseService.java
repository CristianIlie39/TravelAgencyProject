package business.service;

import business.dto.PurchaseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.dao.ClientDAO;
import persistence.dao.FlightDAO;
import persistence.dao.PurchaseDAO;
import persistence.dao.TripDAO;
import persistence.entities.Flight;
import persistence.entities.Purchase;
import persistence.entities.Trip;

import java.util.*;

@Service
public class PurchaseService {

    @Autowired
    PurchaseDAO purchaseDAO;
    @Autowired
    TripDAO tripDAO;
    @Autowired
    ClientDAO clientDAO;
    @Autowired
    FlightDAO flightDAO;
    @Autowired
    TripService tripService;
    @Autowired
    ClientService clientService;

    public void insert(PurchaseDTO purchaseDTO, int numberOfAdults, int numberOfChildren, java.sql.Date flightdepartureDate) {
        Purchase purchase = new Purchase();
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        java.sql.Date dateOfPurchase = new java.sql.Date(date.getTime());
        purchase.setDateOfPurchase(dateOfPurchase); //setam data achizitiei unui trip
        purchase.setAmount(calculateTotalPriceOfTrip(purchaseDTO,numberOfAdults,numberOfChildren,flightdepartureDate)); //am setat pretul total al achizitiei
        purchase.setTrip(tripDAO.findByName(purchaseDTO.getTripDTO().getName())); //am setat tripul de achizitionat
        purchase.setClient(clientDAO.findClientByUsername(purchaseDTO.getClientDTO().getAccountDTO().getUsername())); //am setat clientul care achizitioneaza tripul
        purchaseDAO.insert(purchase);
    }

    public double calculateTotalPriceOfTrip(PurchaseDTO purchaseDTO, int numberOfAdults, int numberOfChildren, java.sql.Date flightDepartureDate) {
        Trip trip = tripDAO.findByName(purchaseDTO.getTripDTO().getName());
        double tripPrice = (trip.getAdultPrice() * numberOfAdults) + (trip.getKidPrice() * numberOfChildren);
        Flight flight = flightDAO.findFlightByDestinationAndByDepartureDateAndByAirport(purchaseDTO.getTripDTO().getHotelDTO().getCityDTO().getName(), flightDepartureDate,purchaseDTO.getTripDTO().getAirportDTO().getName());
        double flightPrice = (numberOfAdults + numberOfChildren) * flight.getPrice();
        double totalAmountOfTrip = tripPrice + flightPrice;
        return totalAmountOfTrip;
    }

    public List<PurchaseDTO> findRecentlyPurchases(java.sql.Date referenceDate) {
        List<Purchase> purchaseList = purchaseDAO.findRecentlyPurchases(referenceDate);
        List<PurchaseDTO> purchaseDTOList = new LinkedList<>();
        for (Purchase purchase : purchaseList) {
            PurchaseDTO purchaseDTO = new PurchaseDTO();
            purchaseDTO.setDateOfPurchase(referenceDate);
            purchaseDTO.setAmount(purchase.getAmount());
            purchaseDTO.setTripDTO(tripService.getTripDTO(purchase.getTrip()));
            purchaseDTO.setClientDTO(clientService.getClientDTO(purchase.getClient()));
            purchaseDTOList.add(purchaseDTO);
        }
        return purchaseDTOList;
    }

    public List<PurchaseDTO> findPurchasesByUsername(String username) {
        List<Purchase> purchaseList = purchaseDAO.findPurchasesByUsername(username);
        List<PurchaseDTO> purchaseDTOList = new LinkedList<>();
        for (Purchase purchase : purchaseList) {
            PurchaseDTO purchaseDTO = new PurchaseDTO();
            purchaseDTO.setDateOfPurchase(purchase.getDateOfPurchase());
            purchaseDTO.setAmount(purchase.getAmount());
            purchaseDTO.setTripDTO(tripService.getTripDTO(purchase.getTrip()));
            purchaseDTO.setClientDTO(clientService.getClientDTO(purchase.getClient()));
            purchaseDTOList.add(purchaseDTO);
        }
        return purchaseDTOList;
    }

    public double calculateTotalAmountSpendByClient(String username) {
        List<Purchase> purchaseList = purchaseDAO.findPurchasesByUsername(username);
        double amount = 0;
        for (Purchase purchase : purchaseList) {
            amount += purchase.getAmount();
        }
        return amount;
    }


}
