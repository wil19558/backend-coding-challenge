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
		return findAndScore(partialName, lattitude, longitude, 10);
	}
	
	public List<CityFinderResult> findAndScore(String partialName, Double lattitude, Double longitude, int maxResult){
		
		
		Collection<City> partialMatchCities = cityRegistry.findCities(partialName);
		LinkedList<CityFinderResult> results = new LinkedList<>();
		
		//Scoring variables
		boolean posAvailable = lattitude != null && longitude != null;
		double minDistance = Double.MAX_VALUE;
		double maxDistance = 0;
		double distance = 1.0;
		
		for(City city : partialMatchCities){
			if(posAvailable){
				//Score is temporarily distance to caller
				distance = city.distanceFrom(lattitude, longitude);
				if(distance > maxDistance){
					maxDistance = distance;
				}
				if(distance < minDistance){
					minDistance = distance;
				}
			}
			
			results.add(new CityFinderResult()
										.withCity(city)
										.withScore(distance));
		}
		
		if(posAvailable){
			for(CityFinderResult result : results){
				result.setScore(scoreFromDistance(result.getScore(), minDistance, maxDistance));
			}
		}
		
		//Sort in score order
		results.sort((CityFinderResult r1, CityFinderResult r2) -> Double.compare(r2.getScore(), r1.getScore()));
		
		if(maxResult > results.size()){
			maxResult = results.size();
		}
		
		return results.subList(0, maxResult);
	}
	
	private double scoreFromDistance(double distance, double minDist, double maxDist){
		minDist = minDist - 1;
		maxDist = maxDist + 1;
		return 1.0 - Math.pow((distance-minDist)/(maxDist-minDist), 3);
	}
}
