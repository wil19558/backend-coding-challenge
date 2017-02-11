package persistence;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import domain.City;
import domain.CityRegistry;

public class PostgreSQLCityRegistry implements CityRegistry {
	private static SessionFactory factory; 
	private static final int JDBC_BATCH_SIZE;
	private static boolean connectionAvailable;
	
	static{
		//Load the actual database url at runtime
		Configuration cfg = new Configuration();
		cfg.setProperty("hibernate.connection.url", System.getenv("DATABASE_URL"));
		JDBC_BATCH_SIZE = Integer.parseInt(cfg.getProperty("hibernate.jdbc.batch_size"));
		connectionAvailable = true;
		try{
			factory = cfg.buildSessionFactory();
		}
		catch(HibernateException e){
			connectionAvailable = false;
		}
	}

	public PostgreSQLCityRegistry() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean isConnectionAvailable(){
		return connectionAvailable;
	}
	
	@Override
	public Collection<City> findCities(String partialName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(City city) {
		// TODO Auto-generated method stub

	}

	@Override
	public void insert(Collection<City> cities) {
		Session session = factory.openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			int i = 0;
			for ( City city : cities ) {
			    session.save(city);
			    if ( i % JDBC_BATCH_SIZE == 0 ) { 
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

	@Override
	public void exportTo(CityRegistry registry) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void clear() {
		
		
	}
	
}
