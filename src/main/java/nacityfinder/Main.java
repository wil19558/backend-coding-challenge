package nacityfinder;

import static spark.Spark.*;

import java.io.IOException;

import persistence.LocalFileCityRegistry;
import persistence.PostgreSQLCityRegistry;

public class Main {

	public static void main(String[] args) {
		new NACityFinderServer().run();
	}

}
