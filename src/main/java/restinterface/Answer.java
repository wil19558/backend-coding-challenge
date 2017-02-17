package restinterface;

import spark.Response;

public class Answer{
	private int httpStatus;
	private String body;
	private String contentType;
	
	public int getHttpStatus() {
		return httpStatus;
	}
	
	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void copyTo(Response sparkResponse){
		sparkResponse.body(body);
		sparkResponse.status(httpStatus);
	}
	
	
}
