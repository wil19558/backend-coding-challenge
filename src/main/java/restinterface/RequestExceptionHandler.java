package restinterface;

/**
 * Project basic REST interface for handling exceptions in request handlers.
 * 
 * @author wil19
 *
 */
public interface RequestExceptionHandler {

	public void process(Exception e, Query query, Answer ans);
	
}
