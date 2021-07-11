package business.service;

import business.dto.ContinentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.dao.ContinentDAO;
import persistence.entities.Continent;

import java.util.LinkedList;
import java.util.List;

@Service
public class ContinentService {

    @Autowired
    ContinentDAO continentDAO;

    public void insert(ContinentDTO continentDTO) {
        Continent continent = new Continent();
        continent.setName(continentDTO.getName());
        continentDAO.insert(continent);
    }

    public ContinentDTO findContinentDTOByName(String name) {
        Continent continent = continentDAO.findContinentByName(name);
        if (continent == null) {
            return null;
        }
        ContinentDTO continentDTO = new ContinentDTO();
        continentDTO.setName(continent.getName());
        return continentDTO;
    }

    public Long countContinentsByName(String name) {
        return continentDAO.countContinentsByName(name);
    }

    public Integer deleteContinentByName(String name) {
        return continentDAO.deleteContinentByName(name);
    }

    public List<ContinentDTO> findAllContinents() {
        List<Continent> continentList = continentDAO.findAllContinents();
        List<ContinentDTO> continentDTOList = new LinkedList<>();
        for (Continent continent : continentList) {
            ContinentDTO continentDTO = new ContinentDTO();
            continentDTO.setName(continent.getName());
            continentDTOList.add(continentDTO);
        }
        return continentDTOList;
    }

    public Integer updateContinentName(String currentName, String newName) {
        return continentDAO.updateContinentName(currentName, newName);
    }

}
