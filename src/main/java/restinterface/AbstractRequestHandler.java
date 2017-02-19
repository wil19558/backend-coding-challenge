package restinterface;

import spark.Request;
import spark.Response;
import spark.Route;

/** 
 * Links the http framework used (currently Spark) to the project rest interface 
 * 
 * @author wil19
 *
 */
public abstract class AbstractRequestHandler implements RequestHandler, Route{

	

	@Override
	public final Object handle(Request req, Response res) throws Exception {
		Query query = new Query(req);
		Answer answer = new Answer();
		
		this.process(query, answer);
		
		answer.copyTo(res);
		return answer.getBody();
	}
	
	@Override
	public abstract void process(Query query, Answer ans) throws Exception;
	
}
