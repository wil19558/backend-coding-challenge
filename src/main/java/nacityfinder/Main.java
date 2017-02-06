package nacityfinder;

import static spark.Spark.*;

public class Main {

	public static void main(String[] args) {
		get("/hello", (req, rep) -> "Hello World");
	}

}
