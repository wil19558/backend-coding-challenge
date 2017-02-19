package restinterface;

import java.util.HashMap;
import java.util.Map;

import spark.Request;

/**
 * 
 * POJO that contains project-specific required parameters.
 * 
 * @author wil19
 *
 */
public class Query {
	
	private Map<String, String[]> queryParams;
	private String body;
	
	public Query(){
		body = "";
		queryParams = new HashMap<String, String[]>();
	}
	
	public Query(Request sparkRequest) {
		body = sparkRequest.body();
		queryParams = sparkRequest.queryMap().toMap();
	}

	public Map<String, String[]> getQueryParams() {
		return queryParams;
	}
	
	public void addQueryParam(String name, String value){
		String[] strings = {value};
		queryParams.put(name, strings);
	}
	
	public String getQueryParam(String name) {
		String value = null;
		if(queryParams.containsKey(name)){
			String[] strings = queryParams.get(name);
			if(strings != null && strings.length > 0){
				value = strings[0];
			}
		}
		
		return value;
	}

	public String getBody() {
		return body;
	}
	
}
