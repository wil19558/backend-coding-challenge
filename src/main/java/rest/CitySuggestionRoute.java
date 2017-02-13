package rest;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;

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
		JsonArrayBuilder jsonArray = Json.createArrayBuilder();
		String partialName = extractQuery(req);
		Double latitude = extractLatitude(req);
		Double longitude = extractLongitude(req);
		
		if(partialName == null || partialName.isEmpty()){
			//response = "Use query param 'q' for name search (/suggestions?q=Londo)";
		}
		else{
			List<CityFinderResult> cities = cityFinder.findAndScore(partialName, latitude, longitude);
			for(CityFinderResult result : cities){
				jsonArray.add(result.asJsonObject());
			}
		}
		
	    resp.type("application/json");
		return jsonArray.build();
	}
	
	private String extractQuery(Request req){
		return req.queryParams("q");
	}
	
	private Double extractLatitude(Request req){
		return extractDouble(req, "latitude");
	}
	
	private Double extractLongitude(Request req){
		return extractDouble(req, "longitude");
	}
	
	private Double extractDouble(Request req, String queryParam){
		Double lat = null;
		try{
			lat = Double.parseDouble(req.queryParams(queryParam));
		}
		catch(Exception e){}
		
		return lat;
	}

}
