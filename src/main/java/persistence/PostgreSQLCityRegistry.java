package persistence;

import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.Transaction;

import domain.City;
import domain.CityRegistry;
import services.SessionFactorySingleton;

public class PostgreSQLCityRegistry implements CityRegistry {
	
	private static SessionFactorySingleton factory = SessionFactorySingleton.getInstance();

	public PostgreSQLCityRegistry() {
		
	}
	
	public boolean isConnectionAvailable(){
		return factory.isConnectionAvailable();
	}
	
	@Override
	public Collection<City> findCities(String partialName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(City city) {
		Session session = openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
		    session.save(city);
	        session.flush();
	        session.clear();
			tx.commit();
		}
		finally {
			session.close();
		}
	}
	
	private Session openSession(){
		return factory.getSessionFactory().openSession();
	}

	@Override
	public void insert(Collection<City> cities) {
		Session session = openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			int i = 0;
			for ( City city : cities ) {
			    session.save(city);
			    if ( i % getJdbcBatchSize() == 0 ) { 
			        //Flush a batch of inserts and release memory
			    	//This makes sure to not overflow memory with big collections
			        session.flush();
			        session.clear();
			    }
			    i++;
			}
			tx.commit();
		}
		finally {
			session.close();
		}
	}
	
	private int getJdbcBatchSize(){
		return factory.getJdbcBatchSize();
	}

	@Override
	public void exportTo(CityRegistry registry) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void clear() {
		
		
	}
	
}
