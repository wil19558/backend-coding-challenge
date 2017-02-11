package rest;

import java.io.IOException;

import persistence.LocalFileCityRegistry;
import persistence.PostgreSQLCityRegistry;
import spark.Request;
import spark.Response;
import spark.Route;

public class LocalPopulateDatabaseRoute implements Route{

	@Override
	public Object handle(Request arg0, Response arg1) throws Exception {
		String result = "";
		//This loads the cities from the local file.
		try{
			LocalFileCityRegistry localRegistry = new LocalFileCityRegistry();
			PostgreSQLCityRegistry remoteRegistry = new PostgreSQLCityRegistry();
			
			if(localRegistry.getCityCount() <= 0){
				throw new Exception("Local file successfully opened, but no cities were loaded.");
			}
			
			if(!remoteRegistry.isConnectionAvailable()){
				throw new Exception("Local file and cities found and loaded, but unable to initiate remote database connection.");
			}
			
			remoteRegistry.clear();
			localRegistry.exportTo(remoteRegistry);
			result = "Successfully populated remote database.";
			
		}
		catch(IOException e){
			result = "Unable to find file. The server is probably not running local.";
		}
		catch(Exception e){
			result = "Error occured : " + e.getMessage();
		}
		
		return result;
	}

}
