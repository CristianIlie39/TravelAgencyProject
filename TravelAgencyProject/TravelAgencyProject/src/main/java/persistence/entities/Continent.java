package persistence.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@NamedQueries({
        @NamedQuery(name = "findContinentByName", query = "SELECT continent FROM Continent continent WHERE continent.name = :name"),
        @NamedQuery(name = "countContinentsByName", query = " SELECT COUNT(*) FROM Continent continent WHERE continent.name = :name"),
        @NamedQuery(name = "deleteContinentByName", query = "DELETE FROM Continent continent WHERE continent.name = :name"),
        @NamedQuery(name = "findAllContinents", query = "SELECT continent FROM Continent continent"),
        @NamedQuery(name = "updateContinentName", query = "UPDATE Continent continent SET continent.name = :newName WHERE continent.name = :currentName")
})

@Entity
@Table(name = "continents")
public class Continent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "continent", fetch = FetchType.LAZY)
    private Set<Country> countrySet;

    public Continent(String name) {
        this.name = name;
    }

    public Continent() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Country> getCountrySet() {
        return countrySet;
    }

    public void setCountrySet(Set<Country> countrySet) {
        this.countrySet = countrySet;
    }

    @Override
    public String toString() {
        return "Continent: " + name;
    }

    public boolean equals(Object object) {
        if (object instanceof Continent) {
            if (this.id == ((Continent) object).getId() && this.name.equals(((Continent) object).getName())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(id) + Objects.hash(name);
    }
}


