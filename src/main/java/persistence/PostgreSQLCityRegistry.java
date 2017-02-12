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
	
	private static final String JDBC_DATABASE_URL = "jdbc:postgresql://ec2-54-221-255-153.compute-1.amazonaws.com:5432/d36apmh339fm0s?"
			+ "sslmode=require&user=faimmhvkknyehm&password=a2e6cb788d384ea01888a3e6bcde271e095a7e0bf3ee45bfe9c589afa6988c5b";
	
	private static final String DATABASE_URL = "postgres://faimmhvkknyehm:a2e6cb788d384ea01888a3e6bcde271e095a7e0bf3ee45bfe9c589afa6988c5b@"
			+ "ec2-54-221-255-153.compute-1.amazonaws.com:5432/d36apmh339fm0s";
	
	private static SessionFactory factory = null; 
	private static int jdbcBatchSize = 1;
	private static boolean connectionAvailable = false;
	
	public static void initSessionFactory(){
		//Load the actual database url at runtime
		Configuration cfg = new Configuration().configure();
		cfg.setProperty("hibernate.connection.url", JDBC_DATABASE_URL);
		jdbcBatchSize = Integer.parseInt(cfg.getProperty("hibernate.jdbc.batch_size"));
		connectionAvailable = true;
		try{
			factory = cfg.buildSessionFactory();
		}
		catch(HibernateException e){
			e.printStackTrace();
			connectionAvailable = false;
		}
	}

	public PostgreSQLCityRegistry() {
		if(connectionAvailable == false){
			//Try to init
			initSessionFactory();
		}
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
			    if ( i % jdbcBatchSize == 0 ) { 
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
