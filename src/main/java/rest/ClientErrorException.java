package rest;

public class ClientErrorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6535888911483942301L;

	
	public ClientErrorException(String message){
		super(message);
	}
}
