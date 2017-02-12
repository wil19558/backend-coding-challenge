package services;

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
}
