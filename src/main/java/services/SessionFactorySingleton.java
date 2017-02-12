package services;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactorySingleton {
	private static SessionFactorySingleton instance = null;
	
	private static final String JDBC_USERNAME = "faimmhvkknyehm";
	private static final String JDBC_PASSWORD = "a2e6cb788d384ea01888a3e6bcde271e095a7e0bf3ee45bfe9c589afa6988c5b";
	
	private static final String JDBC_DATABASE_URL = "jdbc:postgresql://ec2-54-221-255-153.compute-1.amazonaws.com:5432/d36apmh339fm0s?"
			+ "sslmode=require&user=" + JDBC_USERNAME + "&password=" + JDBC_PASSWORD;
	
	
	private SessionFactory factory = null; 
	
	private Configuration configuration;
	private int jdbcBatchSize = 1;
	private boolean connectionAvailable = false;
	
	//Used to disable error print after first stack trace.
	private boolean disablePrintOut = false;
   
	protected SessionFactorySingleton() {
		//Load the actual database url at runtime
		configuration = new Configuration().configure();
		
		configuration.setProperty("hibernate.connection.url", JDBC_DATABASE_URL);
		configuration.setProperty("hibernate.connection.username", JDBC_USERNAME);
		configuration.setProperty("hibernate.connection.password", JDBC_PASSWORD);
		
		jdbcBatchSize = Integer.parseInt(configuration.getProperty("hibernate.jdbc.batch_size"));
		
		attemptToBuildFactory();
	}
	
	private void attemptToBuildFactory(){
		try{
			factory = configuration.buildSessionFactory();
			connectionAvailable = true;
		}
		catch(HibernateException e){
			if(!disablePrintOut){
				System.out.println("Unable to build session factory. Stack trace :");
				e.printStackTrace();
			}
			disablePrintOut = true;
			connectionAvailable = false;
		}
	}
	
	private void verifyConnection(){
		if(!isConnectionAvailable()){
			attemptToBuildFactory();
		}
	}
	
	public boolean isConnectionAvailable(){
		return connectionAvailable;
	}
   
	public SessionFactory getSessionFactory(){
		verifyConnection();
		return factory;
	}
	
	public int getJdbcBatchSize(){
		return jdbcBatchSize;
	}
   
	public static SessionFactorySingleton getInstance() {
		if(instance == null) {
			instance = new SessionFactorySingleton();
		}
		return instance;
	}
}
