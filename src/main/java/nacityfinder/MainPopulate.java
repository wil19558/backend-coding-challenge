package nacityfinder;

import persistence.LocalFileCityRegistry;
import persistence.PostgreSQLCityRegistry;

public class MainPopulate {

	
	public static void main(String[] args) {

		System.out.println("Loading cities from file.");
		//This loads the cities from the local file.
		try{
			LocalFileCityRegistry localRegistry = LocalFileCityRegistry.createDefaultInMemoryRegistry();
			
			if(localRegistry.getCityCount() <= 0){
				throw new Exception("No cities were loaded.");
			}
			
			System.out.println(localRegistry.getCityCount() + " cities added to local (in memory) registry.");

			PostgreSQLCityRegistry remoteRegistry = new PostgreSQLCityRegistry();
			
			if(!remoteRegistry.isConnectionAvailable()){
				throw new Exception("Unable to initiate remote database connection.");
			}
			
			System.out.println("Connection to remote database successful.");
			
			localRegistry.exportTo(remoteRegistry);
			System.out.println("Successfully populated remote database.");
			
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Error occured : " + e.getMessage());
		}
		
	}
}
