package cz.ondrejstastny.mobileiron;

public class AppException extends Exception {

	private static final long serialVersionUID = -8999932578270387947L;
	
	/** 
	 * contains redundantly the HTTP status of the response sent back to the client in case of error, so that
	 * the developer does not have to look into the response headers. If null a default 
	 */
	final Integer status;
	
	/** application specific error code */
	final int code; 
		
	/** link documenting the exception */	
	final String link;
	
	/** detailed error description for developers*/
	final String developerMessage;	
	
	/**
	 * 
	 * @param status
	 * @param code
	 * @param message
	 * @param developerMessage
	 * @param link
	 */
	public AppException(int status, int code, String message,
			String developerMessage, String link) {
		super(message);
		this.status = status;
		this.code = code;
		this.developerMessage = developerMessage;
		this.link = link;
	}

	public int getStatus() {
		return status;
	}

	public int getCode() {
		return code;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public String getLink() {
		return link;
	}
				
}