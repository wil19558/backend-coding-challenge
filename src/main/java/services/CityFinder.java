package services;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import domain.City;
import domain.CityRegistry;

public class CityFinder {
	CityRegistry cityRegistry;
	

	public CityFinder(CityRegistry registry){
		setCityRegistry(registry);
	}
	
	public CityRegistry getCityRegistry() {
		return cityRegistry;
	}

	public void setCityRegistry(CityRegistry cityRegistry) {
		this.cityRegistry = cityRegistry;
	}
	
	public List<CityFinderResult> findAndScore(String partialName){
		return findAndScore(partialName, null, null);
	}
	
	public List<CityFinderResult> findAndScore(String partialName, Double lattitude, Double longitude){
		
		Collection<City> partialMatchCities = cityRegistry.findCities(partialName);
		LinkedList<CityFinderResult> results = new LinkedList<>();
		for(City city : partialMatchCities){
			double score = 0.5;
			//TODO scoring algorithm
			results.add(new CityFinderResult()
										.withCity(city)
										.withScore(score));
		}
		//Sort in score order
		results.sort((CityFinderResult r1, CityFinderResult r2) -> Double.compare(r1.getScore(), r2.getScore()));
		return results;
	}
}
