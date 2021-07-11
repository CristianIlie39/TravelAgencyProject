package business.service;

import business.dto.ContinentDTO;
import business.dto.CountryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.dao.ContinentDAO;
import persistence.dao.CountryDAO;
import persistence.entities.Continent;
import persistence.entities.Country;

import java.util.LinkedList;
import java.util.List;

@Service
public class CountryService {

    @Autowired
    CountryDAO countryDAO;
    @Autowired
    ContinentDAO continentDAO;

    public void insert(CountryDTO countryDTO) {
        Continent continent = new Continent();
        Continent continentFoundByName = continentDAO.findContinentByName(countryDTO.getContinentDTO().getName());
        if (continentFoundByName != null) {
            continent.setId(continentFoundByName.getId());
            continent.setName(continentFoundByName.getName());
        } else {
            continent.setName(countryDTO.getContinentDTO().getName());
        }
        Country country = new Country();
        country.setName(countryDTO.getName());
        country.setContinent(continent);
        countryDAO.insert(country);
    }

    public Long countCountriesByName(String countryName) {
       return countryDAO.countCountriesByName(countryName);
    }

    public CountryDTO findCountryDTOByName(String name) {
        Country countryFoundByName = countryDAO.findCountryByName(name);
        if (countryFoundByName == null) {
            return null;
        } else {
            CountryDTO countryDTO = new CountryDTO();
            countryDTO.setName(countryFoundByName.getName());
            ContinentDTO continentDTO = new ContinentDTO();
            continentDTO.setName(countryFoundByName.getContinent().getName());
            countryDTO.setContinentDTO(continentDTO);
            return countryDTO;
        }
    }

    public List<CountryDTO> findCountriesDTOByContinentDTOName(String continentDTOName) {
        List<Country> countryList = countryDAO.findCountriesByContinentName(continentDTOName);
        List<CountryDTO> countryDTOList = new LinkedList<>();
        for (Country country: countryList) {
            CountryDTO countryDTO = new CountryDTO();
            countryDTO.setName(country.getName());
            ContinentDTO continentDTO = new ContinentDTO();
            continentDTO.setName(country.getContinent().getName());
            countryDTO.setContinentDTO(continentDTO);
            countryDTOList.add(countryDTO);
        }
        return countryDTOList;
    }

    public Integer deleteCountryDTOByName(String name) {
        return countryDAO.deleteCountryByName(name);
    }

    public Integer updateCountryName(String currentName, String newName) {
        return countryDAO.updateCountryName(currentName, newName);
    }

    public List<CountryDTO> findAllCountries() {
        List<Country> countryList = countryDAO.findAllCountries();
        List<CountryDTO> countryDTOList = new LinkedList<>();
        for (Country country : countryList) {
            CountryDTO countryDTO = new CountryDTO();
            countryDTO.setName(country.getName());
            ContinentDTO continentDTO = new ContinentDTO();
            continentDTO.setName(country.getContinent().getName());
            countryDTO.setContinentDTO(continentDTO);
            countryDTOList.add(countryDTO);
        }
        return countryDTOList;
    }

}
