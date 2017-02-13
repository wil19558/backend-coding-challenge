package services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;

import domain.City;

public class CityFinderResult {
	private City city;
	private double score;
	
	public City getCity() {
		return city;
	}
	
	public void setCity(City city) {
		this.city = city;
	}
	
	public CityFinderResult withCity(City city){
		setCity(city);
		return this;
	}
	
	public double getScore() {
		return score;
	}
	
	public void setScore(double score) {
		this.score = score;
	}
	
	public CityFinderResult withScore(double score){
		setScore(score);
		return this;
	}
	
	public JsonObject asJsonObject(){
		return Json.createObjectBuilder()
				.add("name", city.toString())
				.add("latitude", "" + city.getLattitude())
				.add("longitude", "" + city.getLongitude())
				.add("score", roundScore(score))
				.build();
	}
	
	private static double roundScore(double value) {

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(2, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
