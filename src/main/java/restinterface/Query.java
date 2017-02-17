package restinterface;

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
	
	public Query(Request sparkRequest) {
		body = sparkRequest.body();
		queryParams = sparkRequest.queryMap().toMap();
	}

	public Map<String, String[]> getQueryParams() {
		return queryParams;
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
