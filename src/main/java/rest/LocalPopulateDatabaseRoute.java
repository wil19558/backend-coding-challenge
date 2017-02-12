package rest;

import java.io.IOException;
import java.net.URL;

import org.junit.Test;

import persistence.LocalFileCityRegistry;
import persistence.PostgreSQLCityRegistry;
import spark.Request;
import spark.Response;
import spark.Route;

public class LocalPopulateDatabaseRoute implements Route{

	@Override
	public Object handle(Request arg0, Response arg1) throws Exception {
		StringBuilder resultBuilder = new StringBuilder();
		//This loads the cities from the local file.
		try{
			LocalFileCityRegistry localRegistry = new LocalFileCityRegistry();
			PostgreSQLCityRegistry remoteRegistry = new PostgreSQLCityRegistry();
			
			resultBuilder.append("File opened successfully.\n");
			
			if(localRegistry.getCityCount() <= 0){
				throw new Exception("No cities were loaded.");
			}
			
			resultBuilder.append(localRegistry.getCityCount() + " cities added to local (in memory) registry.\n");
			
			if(!remoteRegistry.isConnectionAvailable()){
				throw new Exception("Unable to initiate remote database connection.");
			}
			
			resultBuilder.append("Connection to remote database successful.\n");
			
			remoteRegistry.clear();
			localRegistry.exportTo(remoteRegistry);
			resultBuilder.append("Successfully populated remote database.\n");
			
		}
		catch(IOException e){
			//URL location = Test.class.getProtectionDomain().getCodeSource().getLocation();
			resultBuilder.append("Unable to find file. The server is probably not running local. Current root : " + System.getProperty("user.dir"));
		}
		catch(Exception e){
			resultBuilder.append("Error occured : " + e.getMessage());
		}
		
		return resultBuilder.toString();
	}

}
