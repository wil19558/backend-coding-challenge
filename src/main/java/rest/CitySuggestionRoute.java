package rest;

import java.util.List;

import domain.CityRegistry;
import services.CityFinder;
import services.CityFinderResult;
import spark.Request;
import spark.Response;
import spark.Route;

public class CitySuggestionRoute implements Route {
	
	private CityFinder cityFinder;
	

	public CitySuggestionRoute(CityRegistry registry){
		cityFinder = new CityFinder(registry);
	}
	
	public Object handle(Request req, Response resp) throws Exception {
		String response = "";
		String partialName = req.queryParams("q");
		if(partialName == null || partialName.isEmpty()){
			response = "Use query param 'q' for name search (/suggestions?q=Londo)";
		}
		else{
			List<CityFinderResult> cities = cityFinder.findAndScore(partialName);
			for(CityFinderResult result : cities){
				response = response + result.getCity() + "\r\n";
			}
		}
		return response;
	}

}
