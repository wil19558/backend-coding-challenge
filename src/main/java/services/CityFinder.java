package services;

import java.util.List;

import domain.CityRegistry;

public class CityFinder {
	CityRegistry cityRegistry;
	

	public CityFinder(CityRegistry registry){
		setCityRegistry(cityRegistry);
	}
	
	public CityRegistry getCityRegistry() {
		return cityRegistry;
	}

	public void setCityRegistry(CityRegistry cityRegistry) {
		this.cityRegistry = cityRegistry;
	}
	
	public List<CityFinderResult> findAndScore(String partialName, double lattitude, double longitude){
		return null;
	}
}
