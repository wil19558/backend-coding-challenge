package rest;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import spark.ExceptionHandler;
import spark.Request;
import spark.Response;

public class ClientErrorExceptionHandler implements ExceptionHandler {

	@Override
	public void handle(Exception e, Request req, Response res) {
		JsonObjectBuilder jsonObject = Json.createObjectBuilder();

	    res.status(401);
		jsonObject.add("type", e.getClass().getSimpleName());
		jsonObject.add("message", e.getMessage());

	    res.type("application/json");
		res.body(jsonObject.build().toString());
	}

}
