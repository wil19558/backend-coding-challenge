package persistence;

import java.util.Collection;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

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
		Session session = openSession();
		Collection<City> matches = null;
		try{
			
			CriteriaBuilder critBuilder = session.getCriteriaBuilder();
			CriteriaQuery<City> criteriaQuery = critBuilder.createQuery(City.class);
			Root<City> city = criteriaQuery.from(City.class);
			
			Expression<String> path = city.get("name");
			Predicate like = critBuilder.like(path, partialName);
			
			criteriaQuery.select(city);
			criteriaQuery.where(like);
			
			TypedQuery<City> query = session.createQuery(criteriaQuery);
			
			matches = query.getResultList();
			
	        session.clear();
		}
		finally {
			session.close();
		}
		return matches;
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
			    if ( i % session.getJdbcBatchSize() == 0 ) { 
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
