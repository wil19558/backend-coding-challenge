package rest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import domain.CityRegistry;
import persistence.LocalFileCityRegistry;
import restinterface.Answer;
import restinterface.Query;

public class CitySuggestionRouteTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected=ClientErrorException.class)
	public void testProcess_should_throwClientException_when_noQParam() throws Exception {
		CityRegistry registry = LocalFileCityRegistry.createDefaultInMemoryRegistry();
		Query q = new Query();
		Answer a = new Answer();
		CitySuggestionRoute route = new CitySuggestionRoute(registry);
		
		route.process(q, a);
	}
	
}
