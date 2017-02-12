package nacityfinder;

import java.io.IOException;


import persistence.LocalFileCityRegistry;
import persistence.PostgreSQLCityRegistry;

public class MainPopulate {

	private static final String CITY_REGISTRY_FILE = "/data/cities_canada-usa.tsv"; 
	
	public static void main(String[] args) {

		System.out.println("Loading cities from file.");
		//This loads the cities from the local file.
		try{
			LocalFileCityRegistry localRegistry = new LocalFileCityRegistry();
			PostgreSQLCityRegistry remoteRegistry = new PostgreSQLCityRegistry();
			
			//localRegistry.importRegistryFrom(CITY_REGISTRY_FILE);
			
			System.out.println("File opened successfully.");
			
			if(localRegistry.getCityCount() <= 0){
				//throw new Exception("No cities were loaded.");
			}
			
			System.out.println(localRegistry.getCityCount() + " cities added to local (in memory) registry.");
			
			if(!remoteRegistry.isConnectionAvailable()){
				throw new Exception("Unable to initiate remote database connection.");
			}
			
			System.out.println("Connection to remote database successful.");
			
			remoteRegistry.clear();
			localRegistry.exportTo(remoteRegistry);
			System.out.println("Successfully populated remote database.");
			
		}
		catch(IOException e){
			//URL location = Test.class.getProtectionDomain().getCodeSource().getLocation();
			System.out.println("Unable to find file. The server is probably not running local. Current root : " + System.getProperty("user.dir"));
		}
		catch(Exception e){
			System.out.println("Error occured : " + e.getMessage());
		}
		
	}
}
