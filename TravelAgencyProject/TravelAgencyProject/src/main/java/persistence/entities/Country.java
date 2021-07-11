package persistence.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@NamedQueries({
        @NamedQuery(name = "findCountryByName", query = "SELECT country FROM Country country WHERE country.name = :name"),
        @NamedQuery(name = "findCountriesByContinentName", query = "SELECT country FROM Country country WHERE country.continent.name = :continentName"),
        @NamedQuery(name = "countCountriesByName", query = "SELECT COUNT(*) FROM Country country WHERE country.name = :name"),
        @NamedQuery(name = "deleteCountryByName", query = "DELETE FROM Country country WHERE country.name = :name"),
        @NamedQuery(name = "updateCountryName", query = "UPDATE Country country SET country.name =:newName WHERE country.name = :currentName "),
        @NamedQuery(name = "findAllCountries", query = "SELECT country FROM Country country")
})

@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "continents_id")
    private Continent continent;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private Set<City> citySet;

    public Country(String name, Continent continent) {
        this.name = name;
        this.continent = continent;
    }

    public Country() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<City> getCitySet() {
        return citySet;
    }

    public void setCitySet(Set<City> citySet) {
        this.citySet = citySet;
    }

    @Override
    public String toString() {
        return "Country: " + name + " , " + continent.getName();
    }

    public boolean equals(Object object) {
        if (object instanceof Country) {
            if (this.id == ((Country) object).getId() && this.name.equals(((Country) object).getName()) &&
                    this.continent.equals(((Country) object).getContinent())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(id) + Objects.hash(name) + Objects.hash(continent);
    }
}
