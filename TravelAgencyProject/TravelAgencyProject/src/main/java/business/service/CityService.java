package business.service;

import business.dto.CityDTO;
import business.dto.ContinentDTO;
import business.dto.CountryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.dao.CityDAO;
import persistence.dao.ContinentDAO;
import persistence.dao.CountryDAO;
import persistence.entities.City;
import persistence.entities.Continent;
import persistence.entities.Country;

import java.util.LinkedList;
import java.util.List;

@Service
public class CityService {

    @Autowired
    CityDAO cityDAO;
    @Autowired
    CountryDAO countryDAO;
    @Autowired
    ContinentDAO continentDAO;

    public void insert(CityDTO cityDTO) {
        Continent continent = new Continent();
        Continent continentFound = continentDAO.findContinentByName(cityDTO.getCountryDTO().getContinentDTO().getName());
        if (continentFound != null) {
            continent.setId(continentFound.getId());
            continent.setName(continentFound.getName());
        } else {
            continent.setName(cityDTO.getCountryDTO().getContinentDTO().getName());
        }
        Country country = new Country();
        Country countryFound = countryDAO.findCountryByName(cityDTO.getCountryDTO().getName());
        if (countryFound != null) {
            country.setId(countryFound.getId());
            country.setName(countryFound.getName());
            country.setContinent(countryFound.getContinent());
        } else {
            country.setName(cityDTO.getCountryDTO().getName());
            country.setContinent(continent);
        }
        City city = new City();
        city.setName(cityDTO.getName());
        city.setCountry(country);
        cityDAO.insert(city);
    }

    public Long countCitiesByName(String name) {
        return cityDAO.countCityByName(name);
    }

    public CityDTO findCityDTOByName(String cityDTOName) {
        City cityFound = cityDAO.findCityByName(cityDTOName);
        if (cityFound == null) {
            return null;
        }
        ContinentDTO continentDTO = new ContinentDTO();
        continentDTO.setName(cityFound.getCountry().getContinent().getName());
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setName(cityFound.getCountry().getName());
        countryDTO.setContinentDTO(continentDTO);
        CityDTO cityDTO = new CityDTO();
        cityDTO.setName(cityFound.getName());
        cityDTO.setCountryDTO(countryDTO);
        return cityDTO;
    }

    public Integer deleteCityDTOByName(String name) {
        return cityDAO.deleteCityByName(name);
    }

    public List<CityDTO> findCitiesDTOByCountryDTOName(String countryDTOName) {
        List<City> cityList = cityDAO.findCitiesByCountryName(countryDTOName);
        List<CityDTO> cityDTOList = new LinkedList<>();
        for (City city : cityList) {
            CityDTO cityDTO = new CityDTO();
            cityDTO.setName(city.getName());
            CountryDTO countryDTO = new CountryDTO();
            countryDTO.setName(city.getCountry().getName());
            ContinentDTO continentDTO = new ContinentDTO();
            continentDTO.setName(city.getCountry().getContinent().getName());
            countryDTO.setContinentDTO(continentDTO);
            cityDTO.setCountryDTO(countryDTO);
            cityDTOList.add(cityDTO);
        }
        return cityDTOList;
    }

    public List<CityDTO> findAllCities() {
        List<City> cityList = cityDAO.findAllCities();
        List<CityDTO> cityDTOList = new LinkedList<>();
        for (City city : cityList) {
            CityDTO cityDTO = new CityDTO();
            cityDTO.setName(city.getName());
            CountryDTO countryDTO = new CountryDTO();
            countryDTO.setName(city.getCountry().getName());
            ContinentDTO continentDTO = new ContinentDTO();
            continentDTO.setName(city.getCountry().getContinent().getName());
            countryDTO.setContinentDTO(continentDTO);
            cityDTO.setCountryDTO(countryDTO);
            cityDTOList.add(cityDTO);
        }
        return cityDTOList;
    }

    public Integer updateCityName(String currentName, String newName) {
        return cityDAO.updateCityName(currentName, newName);
    }
}
