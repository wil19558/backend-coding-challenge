package domain;

import java.util.Collection;

public interface CityRegistry {
	
	Collection<City> findCities(String partialName);
	
	void insert(City city);
	
	void insert(Collection<City> cities);
}
