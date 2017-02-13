package nacityfinder;

import static spark.Spark.*;

import persistence.PostgreSQLCityRegistry;
import rest.CitySuggestionRoute;

public class NACityFinderServer {
	
	public void run() {
        startServer();
    }

	private void startServer() {
		
	    port(Integer.valueOf(System.getenv("PORT")));
	    staticFileLocation("/public");
	    
		get("/", (req, rep) -> "Welcome to na-city-finder ! Head down to /suggestions for North American city suggestions.");
		
		get("/suggestions", new CitySuggestionRoute(new PostgreSQLCityRegistry()));
		
		//Basic exception handling
		exception(Exception.class, (e, request, response) -> {
		    response.status(500);
		    response.body("Internal server error:\n" + e.getMessage());
		});
	}
}
