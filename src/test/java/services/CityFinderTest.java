package services;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import domain.City;
import domain.CityRegistry;
import persistence.LocalFileCityRegistry;

public class CityFinderTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindAndScore() throws Exception {
		CityRegistry registry = LocalFileCityRegistry.createDefaultInMemoryRegistry();
		
		assertNotNull(registry);
		
		CityFinder cityFinder = new CityFinder(registry);
		
		//Coveo challenge main exemple
		String partialName = "Londo";
		double latitude= 43.70011;
		double longitude = -79.4163;
		List<CityFinderResult> foundCities = cityFinder.findAndScore(partialName, latitude, longitude);
		int expectedCityId = 6058560;//London, ON, Canada
		boolean found = false;
		for(CityFinderResult result : foundCities){
			if(result.getCity().getCityId() == expectedCityId){
				found = true;
				break;
			}
		}
		
		if(!found){
			fail("Expected London, ON, Canada in results.");
		}
	}

}
