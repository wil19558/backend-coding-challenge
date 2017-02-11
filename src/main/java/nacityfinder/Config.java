package nacityfinder;

public class Config {

	private static final String CITY_REGISTRY_FILE = "/data/cities_canada-usa.tsv"; 
	
	public static String getCityRegistryFilePath(){
		return CITY_REGISTRY_FILE;
	}
}
