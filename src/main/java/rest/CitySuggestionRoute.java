package rest;

import domain.CityRegistry;
import services.CityFinder;
import spark.Request;
import spark.Response;
import spark.Route;

public class CitySuggestionRoute implements Route {
	
	private CityFinder cityFinder;
	

	public CitySuggestionRoute(CityRegistry registry){
		cityFinder = new CityFinder(registry);
	}
	
	public Object handle(Request req, Response resp) throws Exception {
		return null;
	}

}
