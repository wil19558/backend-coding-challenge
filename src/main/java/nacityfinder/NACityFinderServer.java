package nacityfinder;

import static spark.Spark.*;

import javassist.NotFoundException;

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
		
		//Exception handling
		
		exception(NotFoundException.class, (e, request, response) -> {
		    response.status(404);
		    response.body("Resource not found");
		});
		
		exception(Exception.class, (e, request, response) -> {
		    response.status(500);
		    response.body("Internal server error");
		});
	}
}
