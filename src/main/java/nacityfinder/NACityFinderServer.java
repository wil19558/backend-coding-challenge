package nacityfinder;

import static spark.Spark.*;

import persistence.PostgreSQLCityRegistry;


public class NACityFinderServer {
	
	public void run() {
        startServer();
    }

	private void startServer() {
		
	    port(Integer.valueOf(System.getenv("PORT")));
	    staticFileLocation("/public");
	    
		get("/", (req, rep) -> "Hello World");
		
		//Basic exception handling
		exception(Exception.class, (e, request, response) -> {
		    response.status(500);
		    response.body("Internal server error:\n" + e.getMessage());
		});
	}
}
