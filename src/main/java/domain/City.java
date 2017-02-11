package domain;

import java.util.List;
import java.util.function.Consumer;

public class City{
	
	private int dbId;
	private int cityId;
	private String name;
	private String asciiName;
	private double lattitude;
	private double longitude;
	private String country;
	private String state;
	
	public City(){
		
	}

	public int getDbId() {
		return dbId;
	}

	public void setDbId(int dbId) {
		this.dbId = dbId;
	}
	
	public int getCityId() {
		return cityId;
	}
	
	public void setCityId(int id) {
		this.cityId = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAsciiName() {
		return asciiName;
	}
	
	public void setAsciiName(String asciiName) {
		this.asciiName = asciiName;
	}
	
	public double getLattitude() {
		return lattitude;
	}
	
	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * Non-production method. Used rarely to build the database from the tsv file.
	 * Explains the lack of exception handling.
	 * 
	 * @param tsvLine
	 * @return
	 */
	public static void buildFromTSV(City city, String tsvLine){
		String[] tokens = tsvLine.split("\t");
		int token_index = 0;
		for(String token : tokens){
			switch(token_index){
			case 0 : //id
				city.setCityId(Integer.parseInt(token));
				break;
			case 1 : //name
				city.setName(token);
				break;
			case 2 : //ascii name
				city.setAsciiName(token);
				break;
			case 3: // alt_name NOT USED
				break;
			case 4: //lattitude
				city.setLattitude(Double.parseDouble(token));
				break;
			case 5: //longitude
				city.setLongitude(Double.parseDouble(token));
				break;
			case 6: //feat_class NOT USED
				break;
			case 7: //feat_code NOT USED
				break;
			case 8: //country
				city.setCountry(token);
				break;
			case 9: //cc2 NOT USED
				break;
			case 10://admin1 (province/state)
				if(city.getCountry() == "CA"){
					int province_id = Integer.parseInt(token);
					token = canadaProvinceCodeToName(province_id);
				}
				city.setState(token);
				break;
			}
			token_index++;
		}	
	}
	
	private static String canadaProvinceCodeToName(int code){
		String name = "" + code;
		switch(code){
		case 1: //alberta
			name = "AB";
			break;
		case 2: //BC
			name = "BC";
			break;
		case 3: //Manitoba
			name = "MB";
			break;
		case 4: //NB
			name = "NB";
			break;
		case 5: // Newfound
			name = "NL";
			break;
		case 6: // None
			break;
		case 7: //Nova Scotia
			name = "NS";
			break;
		case 8: //Ontario
			name= "ON";
			break;
		case 9: //Prince Ed
			name = "PE";
			break;
		case 10: //Quebec
			name = "QC";
			break;
		case 11: //Sask
			name = "SK";
			break;
		case 12: //Yukon
			name = "YT";
			break;
		case 13: //Northwest
			name = "NT";
			break;
		case 14: //Nunavut
			name = "NU";
			break;
		}
		return name;
	}
}
