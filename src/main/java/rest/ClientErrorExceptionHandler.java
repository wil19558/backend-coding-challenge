package rest;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import restinterface.AbstractExceptionHandler;
import restinterface.Answer;
import restinterface.Query;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;

public class ClientErrorExceptionHandler extends AbstractExceptionHandler {


	@Override
	public void process(Exception e, Query query, Answer ans) {
		JsonObjectBuilder jsonObject = Json.createObjectBuilder();

	    ans.setHttpStatus(401);
		jsonObject.add("type", e.getClass().getSimpleName());
		jsonObject.add("message", e.getMessage());

	    ans.setContentType("application/json");
		ans.setBody(jsonObject.build().toString());
		
	}

}
