package nacityfinder;

import static spark.Spark.*;

public class Main {

	public static void main(String[] args) {
		get("/", (req, rep) -> "Hello World");
	}

}
