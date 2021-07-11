package business.service;

import business.dto.AccountDTO;
import business.dto.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.dao.ClientDAO;
import persistence.entities.Account;
import persistence.entities.Client;

import java.sql.ClientInfoStatus;
import java.util.LinkedList;
import java.util.List;

@Service
public class ClientService {

    @Autowired
    ClientDAO clientDAO;

    public void insert(ClientDTO clientDTO) {
        Client client = new Client();
        client.setSurname(clientDTO.getSurname());
        client.setFirstName(clientDTO.getFirstName());
        client.setYearOfBirth(clientDTO.getYearOfBirth());
        client.setAddress(clientDTO.getAddress());
        client.setPhoneNumber(clientDTO.getPhoneNumber());
        client.setEmail(clientDTO.getEmail());
        Account account = new Account();
        account.setUsername(clientDTO.getAccountDTO().getUsername());
        account.setPassword(clientDTO.getAccountDTO().getPassword());
        account.setAdminRole(clientDTO.getAccountDTO().isAdminRole());
        account.setLoggedIn(clientDTO.getAccountDTO().isLoggedIn());
        client.setAccount(account);
        clientDAO.insert(client);
    }

    public ClientDTO findClientByUsernameAndPassword(String username, String password) {
        Client clientFound = clientDAO.findClientByUsernameAndPassword(username, password);
        if (clientFound == null) {
            return null;
        }
        return getClientDTO(clientFound);
    }

    public ClientDTO findClientByEmail(String email) {
        Client clientFound = clientDAO.findClientByEmail(email);
        if (clientFound == null) {
            return null;
        }
        return getClientDTO(clientFound);
    }

    public ClientDTO findClientByUsername(String username) {
        Client clientFound = clientDAO.findClientByUsername(username);
        if (clientFound == null) {
            return null;
        }
        return getClientDTO(clientFound);
    }

    public ClientDTO getClientDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setSurname(client.getSurname());
        clientDTO.setFirstName(client.getFirstName());
        clientDTO.setYearOfBirth(client.getYearOfBirth());
        clientDTO.setAddress(client.getAddress());
        clientDTO.setPhoneNumber(client.getPhoneNumber());
        clientDTO.setEmail(client.getEmail());
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername(client.getAccount().getUsername());
        accountDTO.setPassword(client.getAccount().getPassword());
        accountDTO.setAdminRole(client.getAccount().isAdminRole());
        accountDTO.setLoggedIn(client.getAccount().isLoggedIn());
        clientDTO.setAccountDTO(accountDTO);
        return clientDTO;
    }

    public List<ClientDTO> findClientsByYearOfBirth(int yearOfBirth) {
        List<Client> clientList = clientDAO.findByYearOfBirth(yearOfBirth);
        List<ClientDTO> clientDTOList = new LinkedList<>();
        for (Client client : clientList) {
            ClientDTO clientDTO = getClientDTO(client);
            clientDTOList.add(clientDTO);
        }
        return clientDTOList;
    }

    public int deleteClientByUsername(String username) {
        return clientDAO.deleteClientByUsername(username);
    }

    public List<ClientDTO> findClientsBySurnameAndFirstName(String surname, String firstName) {
        List<Client> clientList = clientDAO.findClientsBySurnameAndFirstName(surname, firstName);
        List<ClientDTO> clientDTOList = new LinkedList<>();
        for (Client client : clientList) {
            ClientDTO clientDTO = getClientDTO(client);
            clientDTOList.add(clientDTO);
        }
        return clientDTOList;
    }



}
