package restinterface;

/**
 * Project basic REST interface for handling requests.
 * 
 * @author wil19
 *
 */
public interface RequestHandler {
	
	public void process(Query query, Answer ans) throws Exception;
	
}
