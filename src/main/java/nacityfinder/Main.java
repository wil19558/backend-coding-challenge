package nacityfinder;

import static spark.Spark.*;

public class Main {

	public static void main(String[] args) {
	    port(Integer.valueOf(System.getenv("PORT")));
	    staticFileLocation("/public");
	    
		get("/", (req, rep) -> "Hello World");
	}

}
