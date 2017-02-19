package restinterface;

import spark.ExceptionHandler;
import spark.Request;
import spark.Response;

/**
 * Links the http framework used (currently Spark) to the project rest interface 
 * 
 * @author wil19
 *
 */
public abstract class AbstractExceptionHandler implements ExceptionHandler, RequestExceptionHandler {

	

	@Override
	public final void handle(Exception e, Request req, Response res) {
		Query query = new Query(req);
		Answer answer = new Answer();
		
		this.process(e, query, answer);
		
		answer.copyTo(res);
	}
	
	@Override
	public abstract void process(Exception e, Query query, Answer ans);
}
