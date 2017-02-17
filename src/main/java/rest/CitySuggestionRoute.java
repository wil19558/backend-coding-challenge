package rest;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;

import domain.CityRegistry;
import restinterface.AbstractRequestHandler;
import restinterface.Answer;
import restinterface.Query;
import services.CityFinder;
import services.CityFinderResult;
import spark.Request;
import spark.Response;
import spark.Route;

public class CitySuggestionRoute extends AbstractRequestHandler {
	
	private CityFinder cityFinder;
	

	public CitySuggestionRoute(CityRegistry registry){
		cityFinder = new CityFinder(registry);
	}

	@Override
	public void process(Query query, Answer ans) throws Exception {
		JsonArrayBuilder jsonArray = Json.createArrayBuilder();
		String partialName = extractQuery(query);
		Double latitude = extractLatitude(query);
		Double longitude = extractLongitude(query);
		
		if(partialName == null || partialName.isEmpty()){
			throw new ClientErrorException("Use query param 'q' (e.g. ...?q=New York...) to use suggestions feature. "
					+ "Optionnally, use query param latitude and longitude to specify a search origin for the scoring "
					+ "algorithm.");
		}
		else{
			List<CityFinderResult> cities = cityFinder.findAndScore(partialName, latitude, longitude);
			for(CityFinderResult result : cities){
				jsonArray.add(result.asJsonObject());
			}
		}
		
	    ans.setContentType("application/json");
	    ans.setBody(jsonArray.build().toString());
	    ans.setHttpStatus(200);
	}
	
	private String extractQuery(Query query){
		return query.getQueryParam("q");
	}
	
	private Double extractLatitude(Query query){
		return extractDouble(query, "latitude");
	}
	
	private Double extractLongitude(Query query){
		return extractDouble(query, "longitude");
	}
	
	private Double extractDouble(Query query, String queryParam){
		Double lat = null;
		try{
			lat = Double.parseDouble(query.getQueryParam(queryParam));
		}
		catch(Exception e){}
		
		return lat;
	}

}
