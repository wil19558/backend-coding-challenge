package persistence;

import static nacityfinder.Config.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import domain.City;
import domain.CityRegistry;

public class LocalFileCityRegistry implements CityRegistry {
	
	private List<City> registry;
	
	public LocalFileCityRegistry() throws IOException{
		registry = new LinkedList<City>();
		importRegistryFrom(getCityRegistryFilePath());
	}
	
	public void importRegistryFrom(String path) throws IOException{
        Path resolvedPath = Paths.get(path);
		Files.lines(resolvedPath).forEach(s -> {
			City city = new City();
			try{
				City.buildFromTSV(city, s);
				insert(city);
			}
			catch(Exception e){
				//Do nothing with city when error occurs
			}
		});
	}
	
	public int getCityCount(){
		return registry.size();
	}
	
	@Override
	public Collection<City> findCities(String partialName) {
		final int MAX_MATCHES = 10;
		LinkedList<City> matches = new LinkedList<City>();
		partialName = partialName.toLowerCase();
		for(City city : registry){
			if(city.getName().toLowerCase().contains(partialName)){
				matches.add(city);
			}
			if(matches.size() >= MAX_MATCHES){
				break;
			}
		}
		return matches;
	}

	@Override
	public void insert(City city) {
		registry.add(city);
	}

	@Override
	public void insert(Collection<City> cities) {
		for(City city : cities){
			insert(city);
		}
	}

	@Override
	public void exportTo(CityRegistry registry) {
		registry.insert(this.registry);
	}

	@Override
	public void clear() {
		registry.clear();
	}

}
