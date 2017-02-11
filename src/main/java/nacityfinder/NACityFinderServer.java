package nacityfinder;

import static spark.Spark.*;

import javassist.NotFoundException;
import rest.LocalPopulateDatabaseRoute;

public class NACityFinderServer {
	public void run() {
    	prefillDatabase();
        startServer();
    }

	private void prefillDatabase() {
		
	}

	private void startServer() {
	    port(Integer.valueOf(System.getenv("PORT")));
	    staticFileLocation("/public");
	    
		get("/", (req, rep) -> "Hello World");
		
		//Debug route to populate db with file contents
		get("/local-db-populate", new LocalPopulateDatabaseRoute());
		
		//Basic exception handling
		exception(Exception.class, (e, request, response) -> {
		    response.status(500);
		    response.body("Internal server error:\n" + e.getMessage());
		});
	}
}
